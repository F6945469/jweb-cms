/**
 * @author chi
 */
module app.jweb.captcha.web.simple {
    requires com.google.common;
    requires java.xml.bind;
    requires slf4j.api;
    requires aopalliance.repackaged;
    requires javax.annotation.api;
    requires javax.inject;
    requires java.validation;
    requires java.ws.rs;
    requires app.jweb.module;
    requires app.jweb.web;
    requires app.jweb.template;
    requires app.jweb.captcha.web;
    requires simplecaptcha;
    requires java.desktop;

    exports app.jweb.captcha.simple;
}