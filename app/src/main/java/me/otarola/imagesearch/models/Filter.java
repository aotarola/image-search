package me.otarola.imagesearch.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by aotarolaalvarad on 11/1/15.
 */
public class Filter {

    static public List<String> colors = Arrays.asList(
            "Black",
            "Blue",
            "Brown",
            "Gray",
            "Green",
            "Orange",
            "Pink",
            "Purple",
            "Red",
            "Teal",
            "White",
            "Yellow"
    );

    static public List<String> sizes = Arrays.asList(
            "Icon",
            "Small",
            "Medium",
            "Large",
            "XLarge",
            "XXLarge",
            "Huge"
    );
    static public List<String> types = Arrays.asList(
            "Face",
            "Photo",
            "Clipart",
            "Lineart"
    );

    public int selectedColor;
    public int selectedSize;
    public int selectedType;
    public String selectedSite;

    public Filter(){
        selectedColor = selectedSize = selectedType = -1;
    }

    public static String applyFilters(String searchURl, Filter filter) {
        if(filter.selectedColor != -1) {
            searchURl = searchURl + "&imgcolor=" + Filter.colors.get(filter.selectedColor).toLowerCase();
        }
        if(filter.selectedType != -1) {
            searchURl = searchURl + "&imgtype=" + Filter.types.get(filter.selectedType).toLowerCase();
        }
        if(filter.selectedSize != -1) {
            searchURl = searchURl + "&imgsz=" + Filter.sizes.get(filter.selectedSize).toLowerCase();
        }
        if(filter.selectedSite != null){
            searchURl = searchURl + "&as_sitesearch=" + filter.selectedSite.toLowerCase();
        }
        return searchURl;
    }
}
