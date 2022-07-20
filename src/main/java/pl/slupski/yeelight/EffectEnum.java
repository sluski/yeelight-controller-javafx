package pl.slupski.yeelight;

public enum EffectEnum {
    SUDDEN("sudden"),
    SMOOTH("smooth");

    private String value;

    EffectEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
