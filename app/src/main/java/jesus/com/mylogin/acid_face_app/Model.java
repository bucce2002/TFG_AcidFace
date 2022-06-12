package jesus.com.mylogin.acid_face_app;

public class Model {

    private String name;
    private String ImageUrl;

    public Model(){

    }


    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
