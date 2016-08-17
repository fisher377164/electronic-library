package org.fisher.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fisher
 * @since 8/5/16.
 */
@ConfigurationProperties(prefix = "custom", ignoreUnknownFields = false)
public class CustomProperties {

    private final Async async = new Async();

    private final Http http = new Http();

    private final Datasource datasource = new Datasource();

    private final Mail mail = new Mail();

    private final Security security = new Security();

    public Async getAsync() {
        return async;
    }

    public Http getHttp() {
        return http;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public Mail getMail() {
        return mail;
    }

    public Security getSecurity() {
        return security;
    }

    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    public static class Http {

        private final Cache cache = new Cache();

        public Cache getCache() {
            return cache;
        }

        public static class Cache {

            private int timeToLiveInDays = 31;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Datasource {

        private boolean cachePrepStmts = true;

        private int prepStmtCacheSize = 250;

        private int prepStmtCacheSqlLimit = 2048;

        private boolean useServerPrepStmts = true;

        public boolean isCachePrepStmts() {
            return cachePrepStmts;
        }

        public void setCachePrepStmts(boolean cachePrepStmts) {
            this.cachePrepStmts = cachePrepStmts;
        }

        public int getPrepStmtCacheSize() {
            return prepStmtCacheSize;
        }

        public void setPrepStmtCacheSize(int prepStmtCacheSize) {
            this.prepStmtCacheSize = prepStmtCacheSize;
        }

        public int getPrepStmtCacheSqlLimit() {
            return prepStmtCacheSqlLimit;
        }

        public void setPrepStmtCacheSqlLimit(int prepStmtCacheSqlLimit) {
            this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
        }

        public boolean isUseServerPrepStmts() {
            return useServerPrepStmts;
        }

        public void setUseServerPrepStmts(boolean useServerPrepStmts) {
            this.useServerPrepStmts = useServerPrepStmts;
        }
    }

    public static class Mail {

        private String from = "fisher377164@gmail.com";

        private String defaultSubject = "Test";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getDefaultSubject() {
            return defaultSubject;
        }

        public void setDefaultSubject(String defaultSubject) {
            this.defaultSubject = defaultSubject;
        }
    }

    public static class Security {

        private final Token token = new Token();

        public Token getToken() {
            return token;
        }

        public static class Token {

            //expiration in minutes
            private long expiration = 60 * 24 * 30;

            //expiration convert minutes
            public long getExpiration() {
                return expiration * 1000 * 60;
            }

            public void setExpiration(long expiration) {
                this.expiration = expiration;
            }
        }
    }
}