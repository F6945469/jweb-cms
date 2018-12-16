package app.jweb.page.web;

import app.jweb.web.AppInfo;
import app.jweb.web.ClientInfo;
import app.jweb.web.RequestInfo;
import app.jweb.web.UserInfo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author chi
 */
public class Bindings implements Map<String, Object> {
    private final Map<String, Object> bindings;

    public Bindings(Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public AppInfo app() {
        return (AppInfo) bindings.get("app");
    }

    public UserInfo user() {
        return (UserInfo) bindings.get("user");
    }

    public ClientInfo client() {
        return (ClientInfo) bindings.get("client");
    }

    public PostInfo post() {
        return (PostInfo) bindings.get("page");
    }

    public RequestInfo request() {
        return (RequestInfo) bindings.get("request");
    }

    public TemplateInfo template() {
        return (TemplateInfo) bindings.get("template");
    }

    @Override
    public int size() {
        return bindings.size();
    }

    @Override
    public boolean isEmpty() {
        return bindings.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return bindings.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return bindings.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return bindings.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return bindings.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return bindings.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        bindings.putAll(m);
    }

    @Override
    public void clear() {
        bindings.clear();
    }

    @Override
    public Set<String> keySet() {
        return bindings.keySet();
    }

    @Override
    public Collection<Object> values() {
        return bindings.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return bindings.entrySet();
    }
}
