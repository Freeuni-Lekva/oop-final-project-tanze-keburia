package mapper;

public enum TypePageMapper {
    TEXT("Text", "textQuestion.jsp"),
    MULTIPLE_CHOICE("MultipleChoice", "multipleQuestion.jsp"),
    FILL_BLANK("FillBlank", "fillBlank.jsp"),
    PICTURE_RESPONSE("PictureResponse", "pictureResponse.jsp"),
    UNKNOWN("Unknown", "unknownType.jsp");

    private final String typeName;
    private final String jspPage;

    TypePageMapper(String typeName, String jspPage) {
        this.typeName = typeName;
        this.jspPage = jspPage;
    }

    public String getJspPage() {
        return jspPage;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TypePageMapper fromName(String type) {
        for (TypePageMapper qt : values()) {
            if (qt.typeName.equalsIgnoreCase(type)) {
                return qt;
            }
        }
        return UNKNOWN;
    }
}
