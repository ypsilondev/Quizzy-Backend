package de.ypsilon.quizzy.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class EnvironmentVariableWrapper {

    private static EnvironmentVariableWrapper instance;

    private static HashMap<String, String> variables = new HashMap<>();

    public EnvironmentVariableWrapper() {
        if (instance == null) {
            instance = this;
        }
    }

    public void parseVariables(String[] args){
        Optional<String> environmentVariablesArgument = Arrays.stream(args).filter(arg -> arg.startsWith("env:")).findFirst();
        if(environmentVariablesArgument.isPresent()){
            this.registerVariables(environmentVariablesArgument.get().replace("env:", ""));
        }
    }

    public void registerVariables(String vars){
        Arrays.stream(vars.split(";")).map(v -> v.split("=")).forEach(v -> variables.put(v[0], v[1]));
    }

    public String getenv(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        }
        return System.getenv(key);
    }

    public static EnvironmentVariableWrapper getInstance() {
        return instance;
    }
}
