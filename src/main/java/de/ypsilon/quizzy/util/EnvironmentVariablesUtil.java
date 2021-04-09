package de.ypsilon.quizzy.util;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class EnvironmentVariablesUtil {

    private static EnvironmentVariablesUtil instance;

    private static final HashMap<String, String> variables = new HashMap<>();

    /**
     * Creates a new instance of the {@link EnvironmentVariablesUtil}, saves it as instance, if called the first time
     */
    public EnvironmentVariablesUtil() {
        if (instance == null) {
            instance = this;
        }
    }

    /**
     * Parses the CLI-arguments, searching the "env:...." one.
     *
     * @param args the CLI-arguments
     */
    public void parseVariables(String[] args) {
        Optional<String> environmentVariablesArgument = Arrays.stream(args).filter(arg -> arg.startsWith("env:")).findFirst();
        if (environmentVariablesArgument.isPresent()) {
            this.registerVariables(environmentVariablesArgument.get().replace("env:", ""));
        }
    }

    /**
     * Registers all the variables in the given semicolon-separated string.
     *
     * @param vars the semicolon-separated string containing the variables.
     */
    public void registerVariables(String vars) {
        Arrays.stream(vars.split(";")).map(v -> v.split("=")).forEach(v -> variables.put(v[0], v[1]));
    }

    /***
     * Loads an environment-variable by its name. First it'll be searched in the CLI-arguments, if not found, it'll be searched in the real environment-variables of the host-system.
     * @param key the name of the environment-variable
     * @return the value of the environmen-variable or null.
     */
    @Nullable
    public String getenv(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        }
        return System.getenv(key);
    }

    /**
     * Gets the {@link EnvironmentVariablesUtil} instance
     *
     * @return the instance
     */
    public static EnvironmentVariablesUtil getInstance() {
        return instance;
    }
}
