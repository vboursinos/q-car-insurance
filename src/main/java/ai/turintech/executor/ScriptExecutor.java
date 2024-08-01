package ai.turintech.executor;

public interface ScriptExecutor {

    public void executeQScript(String qScript, String kdbHost, String kdbPort);

    public Object executeQScriptWithReturn(String qScript, String kdbHost, String kdbPort);
}
