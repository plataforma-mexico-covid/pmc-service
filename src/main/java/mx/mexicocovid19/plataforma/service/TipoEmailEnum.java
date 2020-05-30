package mx.mexicocovid19.plataforma.service;

public enum TipoEmailEnum {
    REGISTRO_VOLUNTARIO("email/registroVoluntario.vm", "¡Bienvenido a bordo!"),
    REGISTRO_USUARIO("email/registroUsuario.vm", "¡Bienvenido a bordo!"),
    OFRECE_AYUDA("email/ofreceAyuda.vm", "¡Gracias por sumar!"),
    SOLICITA_AYUDA("email/solicitaAyuda.vm", "¡Gracias tu solicitud fue registrada!"),
    MATCH_AYUDA("email/matchAyuda.vm", "¡Es momento de hacer equipo!"),
    RECUPERACION_PASSWORD("email/recoveryPassword.vm", "Recuperar password"),

    MATCH_OFERTA("email/matchOferta.vm", "¡Es momento de hacer equipo!"),
    REGISTRO_OFERTA("email/registroOferta.vm", "¡Recibimos tu oferta!");

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
