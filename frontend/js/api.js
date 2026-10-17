// api.js — Backend iletişim yardımcıları
// Tüm API yanıtları { success, data, message, timestamp } biçiminde sarılır.
// Her istek için, yoksa (localStorage) saklanan token başlığa eklenir.

const API_BASE = "http://localhost:8080";

const AuthStore = {
    getToken() {
        return localStorage.getItem("bap_access_token") ||
            sessionStorage.getItem("bap_access_token") || "";
    },
    setToken(token) {
        sessionStorage.setItem("bap_access_token", token);
    },
    clear() {
        sessionStorage.removeItem("bap_access_token");
    }
};

async function apiFetch(path, options = {}) {
    const headers = new Headers(options.headers || {});
    const token = AuthStore.getToken();
    if (token) {
        headers.set("Authorization", `Bearer ${token}`);
    }
    if (options.body && !(options.body instanceof FormData) &&
        typeof options.body === "string") {
        if (!headers.has("Content-Type")) {
            headers.set("Content-Type", "application/json");
        }
    }

    const response = await fetch(`${API_BASE}${path}`, {
        ...options,
        headers
    });

    let payload = null;
    const contentType = response.headers.get("content-type") || "";
    if (contentType.includes("application/json")) {
        payload = await response.json();
    }

    if (!response.ok) {
        const message = payload?.message || `HTTP Hatası: ${response.status}`;
        const error = new Error(message);
        error.status = response.status;
        error.payload = payload;
        throw error;
    }

    // ApiResponse sarmalayıcısını aç
    return payload?.data ?? payload;
}
