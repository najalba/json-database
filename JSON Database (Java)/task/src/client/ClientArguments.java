package client;

import com.beust.jcommander.Parameter;

import java.util.List;
import java.util.Objects;

public class ClientArguments {
    @Parameter(names = {"-in"})
    private String inFile;
    @Parameter(names = {"-t", "--type"})
    private String type;
    @Parameter(names = {"-k", "--key"})
    private String key;
    @Parameter(names = {"-v", "--value"}, variableArity = true)
    private List<String> value;

    public String getInFile() {
        return inFile;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return Objects.nonNull(this.value) ? String.join(" ", this.value) : "";
    }
}
