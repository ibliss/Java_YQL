package org.blissard.javayql;

public class QueryConfiguration {

    public static final QueryConfiguration DEFAULT = new QueryConfiguration();
    public static final QueryConfiguration DATATABLES = new QueryConfiguration(Environment.DATATABLES);
    
    // Environments
    public static class Environment {
        public static final String DEFAULT = null;
        public static final String DATATABLES = "http://datatables.org/alltables.env";
    }
    
    // Table names
    public static class Tables {
        public static class AllTables {
            public static class Finance {
                public static final String quotes = "yahoo.finance.quotes";
                public static final String historicaldata = "yahoo.finance.historicaldata";
            }
        }
    }
    
    public static final String DEFAULT_BASE_URL = "http://query.yahooapis.com/v1/public/yql";
    public static final String DEFAULT_QUERY_URL = "q";
    public static final String DEFAULT_FORMAT_VAR = "format";
    public static final String DEFAULT_ENV_VAR = "env";
    public static final String DEFAULT_FORMAT = "json";
    
    protected String    baseURL,
                        queryVar,
                        formatVar,
                        format,
                        env,
                        envVar;
    
    public QueryConfiguration() {
        this(Environment.DEFAULT);
    }
    
    public QueryConfiguration(String environment) {
        this(DEFAULT_BASE_URL, DEFAULT_QUERY_URL, DEFAULT_FORMAT_VAR, DEFAULT_ENV_VAR);
        format = DEFAULT_FORMAT; 
        env = environment;
    }
    
    public QueryConfiguration(String base_url, String query_var, String format_var, String env_var) {
        this.baseURL = base_url;
        this.queryVar = query_var;
        this.formatVar = format_var;
        this.envVar = env_var;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String base_url) {
        this.baseURL = base_url;
    }

    public String getQueryVar() {
        return queryVar;
    }

    public void setQueryVar(String query_var) {
        this.queryVar = query_var;
    }

    public String getFormatVar() {
        return formatVar;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean hasEnv() {
        return this.env != null;
    }
    
    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getEnvVar() {
        return envVar;
    }

    public void setEnvVar(String env_var) {
        this.envVar = env_var;
    }

}
