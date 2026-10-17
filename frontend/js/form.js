// form.js — Dinamik proje başvuru formu
// API yanıtları api.js içindeki apiFetch ile ApiResponse sarmalayıcısından açılır.
// Dinamik yapılar korunur:
//   * "Ekle" butonu ile anahtar kelime girişi dinamik eklenir.
//   * Temel alan seçildiğinde alt alan listesi dinamik yüklenir.
//   * Proje türü ve temel alan listeleri API'den dinamik doldurulur.

async function loadProjectTypes() {
    const select = document.getElementById("project_type");

    try {
        const data = await apiFetch("/api/project-types");

        select.innerHTML = '<option value="" disabled selected>Bir proje türü seçin...</option>';

        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.kod;
            option.textContent = item.ad;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Proje türleri yüklenemedi:", error);
        select.innerHTML = '<option value="" disabled>Veriler yüklenemedi</option>';
    }
}

const div = document.getElementById("keyvalue");
const button = document.getElementById("ekle");
const anahtarDurum = document.getElementById("anahtarDurum");



button.addEventListener("click", function () {
    const input = document.createElement("input");
    const br = document.createElement("br");
    input.type = "text";
    input.placeholder = "Bir anahtar kelime girin...";
    input.classList.add("dynamic-input");
    div.appendChild(input);
    div.appendChild(br);
});

const button2 = document.getElementById("kaydet");
const baslik = document.getElementById("baslik");

button2.addEventListener("click", function () {
   if(baslik.value == ""){
        alert("proje başlığını giriniz!");
    }

     const inputlar = document.getElementsByClassName("dynamic-input");

    if( inputlar.length < 2 ) {
        alert('en az 2 anahtar kelime eklemelisin');
        baslik.style.backgroundColor = "yellow";
        return false;
    }
     
    for (const input of inputlar) {
        const temizDeger = input.value.trim();
        if (temizDeger == "") {
             alert("lutfen anahtar kelimeleri giriniz!");
             return false;
        }
    }

    const payload = {
        baslik: document.getElementById("baslik").value,
        ozet: document.getElementById("ozet").value,
        anahtarKelimeler: inputlar,
        temelAlanGrupu: temelAlanSelect.value,
        altAlanKod: altAlanSelect.value,
        projectTypeKod: document.getElementById("project_type").value,
        startDate: document.getElementById("startDate").value,
        endDate: document.getElementById("endDate").value,
        butceTalebi: document.getElementById("butceTalebi").value,
        arastirmaci: document.getElementById("arastirmaci").value
    };

    //http post istegi (http request) backend proje kaydetme endpointine (api, web sevisine ) atılır
    // cevap alınınca (http response ) gerekli atraksiyonlar gerçekleştirilir.
    console.log("Form verisi:", payload);
});

const temelAlanSelect = document.getElementById("temelAlan");
const altAlanSelect = document.getElementById("subField");
const altAlanDurum = document.getElementById("altAlanDurum");

async function loadProjectFields() {
    try {
        const data = await apiFetch("/api/temel-alanlar");

        temelAlanSelect.innerHTML = '<option value="" disabled selected>Bir temel alan seçin...</option>';

        data.forEach(field => {
            const option = document.createElement("option");
            option.value = field.id;
            option.textContent = field.ad;
            temelAlanSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Temel alanlar yüklenemedi:", error);
        temelAlanSelect.innerHTML = '<option value="" disabled>Veriler yüklenemedi</option>';
    }
}

temelAlanSelect.addEventListener("change", async (event) => {
    const secilenTemelAlanId = event.target.value;

    altAlanSelect.innerHTML = '<option value="" disabled selected>Yükleniyor...</option>';
    altAlanSelect.disabled = true;
    altAlanDurum.textContent = "Alt alanlar yükleniyor...";

    if (!secilenTemelAlanId) {
        altAlanSelect.innerHTML = '<option value="" disabled selected>alt alan seçiniz...</option>';
        altAlanDurum.textContent = "";
        return;
    }

    try {
        const subFields = await apiFetch(`/api/temel-alanlar/${secilenTemelAlanId}/alt-alanlar`);

        altAlanSelect.innerHTML = '<option value="" disabled selected>Bir alt alan seçin...</option>';

        if (subFields.length === 0) {
            altAlanSelect.innerHTML = '<option value="" disabled selected>(bu temel alanda alt alan yok)</option>';
            altAlanDurum.textContent = "Bu temel alana bağlı alt alan bulunamadı.";
        } else {
            subFields.forEach(altAlan => {
                const option = document.createElement("option");
                option.value = altAlan.id;
                option.textContent = altAlan.ad;
                altAlanSelect.appendChild(option);
            });
            altAlanDurum.textContent = subFields.length + " alt alan bulundu.";
        }

        altAlanSelect.disabled = false;
    } catch (error) {
        console.error("Alt alanlar yüklenemedi:", error);
        altAlanSelect.innerHTML = '<option value="" disabled>Veriler yüklenemedi</option>';
        altAlanDurum.textContent = "Alt alanlar yüklenemedi.";
    }
});

// Fonksiyonları çalıştır
loadProjectTypes();
loadProjectFields();
