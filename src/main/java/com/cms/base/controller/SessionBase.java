package com.cms.base.controller;

import jakarta.servlet.http.HttpSession;

public class SessionBase {

    /**
     * Sessionに値を保存する
     */
    protected void setLoginInfo(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    /**
     * Sessionから値を取得する
     */
    @SuppressWarnings("unchecked")
    protected <T> T getLoginInfo(HttpSession session, String key, Class<T> type) {
        Object value = session.getAttribute(key);
        if (value == null) {
            return null;
        }
        if (type.isInstance(value)) {
            return (T) value;
        }
        throw new ClassCastException("Session attribute [" + key + "] is not of type " + type.getName());
    }

    /**
     * Sessionから値を削除する
     */
    protected void removeSessionAttribute(HttpSession session, String key) {
        session.removeAttribute(key);
    }
}