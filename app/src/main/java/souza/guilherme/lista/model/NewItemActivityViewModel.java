package souza.guilherme.lista.model;

import androidx.lifecycle.ViewModel;
import android.net.Uri;

public class NewItemActivityViewModel extends ViewModel {
    Uri selectPhotoLocation = null;
    public Uri getSelectPhotoLocation(){
        return selectPhotoLocation;
    }

    public void setSelectPhotoLocation(Uri selectPhotoLocation){
        this.selectPhotoLocation = selectPhotoLocation;
    }
}
