package com.github.nikyotensai.feign.fallback.proxy;


import com.github.nikyotensai.feign.fallback.handle.HandleContext;


class HandleContextHolder {

    private static final ThreadLocal<HandleContext> HANDLE_CONTEXT_THREADLOCAL = new ThreadLocal<>();

    static HandleContext getLocalHandleContext() {
        return HANDLE_CONTEXT_THREADLOCAL.get();
    }

    static void setLocalHandleContext(HandleContext context) {
        HANDLE_CONTEXT_THREADLOCAL.set(context);
    }

    static void removeLocalHandleContext() {
        HANDLE_CONTEXT_THREADLOCAL.remove();
    }

}
