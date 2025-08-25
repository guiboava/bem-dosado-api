package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.PatientContact;

public record PatientContactRequestDTO(String name,
                                       String phoneNumber,
                                       String affiliation) {

    public PatientContact createPatientContact() {
        PatientContact patientContact = new PatientContact();
        patientContact.setName(name);
        patientContact.setPhoneNumber(phoneNumber);
        patientContact.setAffiliation(affiliation);
        return patientContact;
    }

}
