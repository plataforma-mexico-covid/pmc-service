package mx.mexicocovid19.plataforma.service;

public enum TipoEmailEnum {
    REGISTRO_USUARIO("email/registroUsuario.vm", "¡Bienvenido a bordo!"),
    OFRECE_AYUDA("email/ofreceAyuda.vm", "¡Gracias por sumar!"),
    SOLICITA_AYUDA("email/solicitaAyuda.vm", "¡Gracias tu solicitud fue registrada!"),
    MATCH_AYUDA("email/matchAyuda.vm", "¡Es momento de hacer equipo!"),
    RECUPERACION_PASSWORD("email/recoveryPassword.vm", "Recuperar password"),;

    private String template;
    private String subject;

    TipoEmailEnum(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public String getTemplate() {
        return this.template;
    }

    public String getSubject() {
        return this.subject;
    }
}
