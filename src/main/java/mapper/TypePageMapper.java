package mapper;

public class TypePageMapper {
    public static String getPageForType(String type) {
        switch (type) {
            case "Text": return "textQuestion.jsp";
            case "MultipleChoice": return "multipleQuestion.jsp";
            case "FillBlank":return "fillBlank.jsp";
            case "PictureResponse":return "pictureResponse.jsp";
            default: return "unknownType.jsp";
        }
    }
}
