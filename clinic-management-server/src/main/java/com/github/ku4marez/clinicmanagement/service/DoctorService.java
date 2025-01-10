package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.commonlibraries.dto.DoctorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    Page<DoctorDTO> searchDoctor(String name, Pageable pageable);

    DoctorDTO createDoctor(DoctorDTO doctor);

    Page<DoctorDTO> getAllDoctors(Pageable pageable);

    DoctorDTO findDoctorByEmailCaseSensitive(String email);

    DoctorDTO updateDoctor(String id, DoctorDTO doctor);

    void deleteDoctor(String id);
}
