package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.ProjectTypeCreateRequest;
import com.mersin.bap_otomasyon.dto.request.ProjectTypeUpdateRequest;
import com.mersin.bap_otomasyon.dto.response.ProjectTypeResponse;
import com.mersin.bap_otomasyon.entity.ProjectType;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.repository.ProjectTypeRepository;
import com.mersin.bap_otomasyon.service.ProjectTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectTypeServiceImpl implements ProjectTypeService {

    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectRepository projectRepository;

    public ProjectTypeServiceImpl(ProjectTypeRepository projectTypeRepository,
                                  ProjectRepository projectRepository) {
        this.projectTypeRepository = projectTypeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectTypeResponse> findAll() {
        return projectTypeRepository.findAllByAktifTrue()
                .stream()
                .map(ProjectTypeResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectTypeResponse findByKod(String kod) {
        ProjectType entity = projectTypeRepository.findByKod(kod)
                .orElseThrow(() -> new EntityNotFoundException("Proje turu bulunamadi: " + kod));
        return ProjectTypeResponse.from(entity);
    }

    @Override
    public ProjectTypeResponse create(ProjectTypeCreateRequest request) {
        if (projectTypeRepository.findByKod(request.getKod()).isPresent()) {
            throw new BusinessException("BAP-PTY-001", "Bu kodla kayitli proje turu var: " + request.getKod());
        }

        ProjectType entity = new ProjectType();
        entity.setKod(request.getKod());
        entity.setAd(request.getAd());
        entity.setButceTavani(request.getButceTavani());
        entity.setMaxSureAy(request.getMaxSureAy());
        if (request.getMinSureAy() != null) entity.setMinSureAy(request.getMinSureAy());
        if (request.getEkButceOran() != null) entity.setEkButceOran(request.getEkButceOran());
        if (request.getEkSureAy() != null) entity.setEkSureAy(request.getEkSureAy());
        if (request.getPerformansArtisiUygulanir() != null) entity.setPerformansArtisiUygulanir(request.getPerformansArtisiUygulanir());
        if (request.getBursiyerOlabilir() != null) entity.setBursiyerOlabilir(request.getBursiyerOlabilir());
        if (request.getYayinSartiZorunlu() != null) entity.setYayinSartiZorunlu(request.getYayinSartiZorunlu());
        if (request.getKariyerBoyuKez() != null) entity.setKariyerBoyuKez(request.getKariyerBoyuKez());
        if (request.getYildaKez() != null) entity.setYildaKez(request.getYildaKez());
        if (request.getEszamanliLimit() != null) entity.setEszamanliLimit(request.getEszamanliLimit());
        if (request.getHakemAtanabilir() != null) entity.setHakemAtanabilir(request.getHakemAtanabilir());
        if (request.getSozluSunumIstenebilir() != null) entity.setSozluSunumIstenebilir(request.getSozluSunumIstenebilir());
        if (request.getAraRaporGerekli() != null) entity.setAraRaporGerekli(request.getAraRaporGerekli());
        if (request.getKongreDestegiVar() != null) entity.setKongreDestegiVar(request.getKongreDestegiVar());
        if (request.getProtokolGerekli() != null) entity.setProtokolGerekli(request.getProtokolGerekli());
        if (request.getRektorOnayiGerekli() != null) entity.setRektorOnayiGerekli(request.getRektorOnayiGerekli());
        if (request.getAktif() != null) entity.setAktif(request.getAktif());

        ProjectType saved = projectTypeRepository.save(entity);
        return ProjectTypeResponse.from(saved);
    }

    @Override
    public void delete(String kod) {
        ProjectType existing = projectTypeRepository.findByKod(kod)
                .orElseThrow(() -> new EntityNotFoundException("Proje turu bulunamadi: " + kod));

        if (!projectRepository.findByProjectTypeId(existing.getId()).isEmpty()) {
            throw new BusinessException("BAP-PTY-002", "Bu proje turune bagli projeler var, silinemez: " + kod);
        }

        projectTypeRepository.delete(existing);
    }

    @Override
    public ProjectTypeResponse update(String kod, ProjectTypeUpdateRequest request) {
        ProjectType existing = projectTypeRepository.findByKod(kod)
                .orElseThrow(() -> new EntityNotFoundException("Proje turu bulunamadi: " + kod));

        if (request.getAd() != null) existing.setAd(request.getAd());
        if (request.getButceTavani() != null) existing.setButceTavani(request.getButceTavani());
        if (request.getMinSureAy() != null) existing.setMinSureAy(request.getMinSureAy());
        if (request.getMaxSureAy() != null) existing.setMaxSureAy(request.getMaxSureAy());
        if (request.getEkButceOran() != null) existing.setEkButceOran(request.getEkButceOran());
        if (request.getEkSureAy() != null) existing.setEkSureAy(request.getEkSureAy());
        if (request.getPerformansArtisiUygulanir() != null) existing.setPerformansArtisiUygulanir(request.getPerformansArtisiUygulanir());
        if (request.getBursiyerOlabilir() != null) existing.setBursiyerOlabilir(request.getBursiyerOlabilir());
        if (request.getYayinSartiZorunlu() != null) existing.setYayinSartiZorunlu(request.getYayinSartiZorunlu());
        if (request.getKariyerBoyuKez() != null) existing.setKariyerBoyuKez(request.getKariyerBoyuKez());
        if (request.getYildaKez() != null) existing.setYildaKez(request.getYildaKez());
        if (request.getEszamanliLimit() != null) existing.setEszamanliLimit(request.getEszamanliLimit());
        if (request.getHakemAtanabilir() != null) existing.setHakemAtanabilir(request.getHakemAtanabilir());
        if (request.getSozluSunumIstenebilir() != null) existing.setSozluSunumIstenebilir(request.getSozluSunumIstenebilir());
        if (request.getAraRaporGerekli() != null) existing.setAraRaporGerekli(request.getAraRaporGerekli());
        if (request.getKongreDestegiVar() != null) existing.setKongreDestegiVar(request.getKongreDestegiVar());
        if (request.getProtokolGerekli() != null) existing.setProtokolGerekli(request.getProtokolGerekli());
        if (request.getRektorOnayiGerekli() != null) existing.setRektorOnayiGerekli(request.getRektorOnayiGerekli());
        if (request.getAktif() != null) existing.setAktif(request.getAktif());

        ProjectType saved = projectTypeRepository.save(existing);
        return ProjectTypeResponse.from(saved);
    }
}
