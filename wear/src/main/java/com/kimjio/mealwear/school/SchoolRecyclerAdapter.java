package com.kimjio.mealwear.school;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimjio.mealwear.Preference;
import com.kimjio.mealwear.R;
import com.kimjio.mealwear.country.CountrySelectActivity;
import com.kimjio.mealwear.list.SchoolSelectListActivity;
import com.kimjio.mealwear.meal.MealActivity;
import com.kimjio.mealwear.type.SchoolTypeActivity;

import org.hyunjun.school.School;

import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;
import static com.kimjio.mealwear.school.SchoolSelectActivity.REQUEST_COUNTRY;
import static com.kimjio.mealwear.school.SchoolSelectActivity.REQUEST_SCHOOL_SELECT;
import static com.kimjio.mealwear.school.SchoolSelectActivity.REQUEST_SCHOOL_TYPE;

/**
 * Created by kimji on 2017-10-15.
 */

public class SchoolRecyclerAdapter extends WearableRecyclerView.Adapter<WearableRecyclerView.ViewHolder> {

    private Activity activity;

    private String schoolCountry;
    private int schoolType;
    private EditText editText;
    private TextView countryTextView;
    private TextView schoolTypeTextView;
    private static int schoolCountryID = -1;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_COUNTRY = 1;
    private static final int TYPE_TYPE = 2;
    private static final int TYPE_EDIT = 3;
    private static final int TYPE_SEARCH = 4;
    private static final int TYPE_OPOP = 5;
    private static final int TYPE_FOOTER = 6;
    private static final int TYPE_ITEM = 7;

    public SchoolRecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_HEADER;
        } else if (1 == position) {
            return TYPE_COUNTRY;
        } else if (2 == position) {
            return TYPE_TYPE;
        } else if (3 == position) {
            return TYPE_EDIT;
        } else if (4 == position) {
            return TYPE_SEARCH;
        } else if (5 == position) {
            return TYPE_OPOP;
        } else if (6 == position) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public WearableRecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_title_item, viewGroup, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_item, viewGroup, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_COUNTRY) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_country_button, viewGroup, false);
            return new CountryButtonViewHolder(view);
        } else if (viewType == TYPE_EDIT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_school_input, viewGroup, false);
            return new SchoolEditTextViewHolder(view);
        } else if (viewType == TYPE_TYPE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_type_button, viewGroup, false);
            return new TypeButtonViewHolder(view);
        } else if (viewType == TYPE_OPOP) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_open_on_phone, viewGroup, false);
            return new OpenOnPhoneButtonViewHolder(view);
        } else if (viewType == TYPE_SEARCH) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_school_search_button, viewGroup, false);
            return new SearchButtonViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(WearableRecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof CountryButtonViewHolder) {
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivityForResult(new Intent(activity, CountrySelectActivity.class), REQUEST_COUNTRY);
                }
            };
            CountryButtonViewHolder countryButtonViewHolder = (CountryButtonViewHolder) viewHolder;
            countryTextView = countryButtonViewHolder.countryText;
            countryButtonViewHolder.imageView.setOnClickListener(view);
            countryButtonViewHolder.countryText.setOnClickListener(view);
            countryButtonViewHolder.itemView.setOnClickListener(view);
        } else if (viewHolder instanceof TypeButtonViewHolder) {
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivityForResult(new Intent(activity, SchoolTypeActivity.class), REQUEST_SCHOOL_TYPE);
                }
            };
            TypeButtonViewHolder typeButtonViewHolder = (TypeButtonViewHolder) viewHolder;
            schoolTypeTextView = typeButtonViewHolder.typeText;
            typeButtonViewHolder.imageView.setOnClickListener(view);
            typeButtonViewHolder.typeText.setOnClickListener(view);
            typeButtonViewHolder.itemView.setOnClickListener(view);
        } else if (viewHolder instanceof SchoolEditTextViewHolder) {
            SchoolEditTextViewHolder schoolEditTextViewHolder = (SchoolEditTextViewHolder) viewHolder;
            editText = schoolEditTextViewHolder.editText;
        } else if (viewHolder instanceof OpenOnPhoneButtonViewHolder) {
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ConfirmationActivity.class).putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION).putExtra(ConfirmationActivity.EXTRA_MESSAGE, "휴대전화에서 확인"));
                }
            };
            OpenOnPhoneButtonViewHolder openOnPhoneButtonViewHolder = (OpenOnPhoneButtonViewHolder) viewHolder;
            openOnPhoneButtonViewHolder.imageView.setOnClickListener(view);
            openOnPhoneButtonViewHolder.typeText.setOnClickListener(view);
            openOnPhoneButtonViewHolder.itemView.setOnClickListener(view);
        } else if (viewHolder instanceof SearchButtonViewHolder) {
            View.OnClickListener view = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivityForResult(new Intent(activity, SchoolSelectListActivity.class).putExtra("schoolName", editText.getText().toString()).putExtra("schoolType", schoolType).putExtra("schoolCountry", schoolCountry), REQUEST_SCHOOL_SELECT);
                }
            };
            SearchButtonViewHolder searchButtonViewHolder = (SearchButtonViewHolder) viewHolder;
            searchButtonViewHolder.imageView.setOnClickListener(view);
            searchButtonViewHolder.itemView.setOnClickListener(view);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_COUNTRY) {
            if (resultCode == RESULT_OK) {
                schoolCountryID = intent.getIntExtra("schoolCountryID", -1);
                new Preference(activity).putInt("schoolCountryID", schoolCountryID);
                ArrayList<String> countryList = new ArrayList<>(Arrays.asList(activity.getResources().getStringArray(R.array.selContEducation)));
                for (int i = 0; i < countryList.size(); i++) {
                    if (schoolCountryID == i) {
                        countryTextView.setText(countryList.get(i));
                    }
                }
                ArrayList<String> countryListUrls = new ArrayList<>(Arrays.asList(activity.getResources().getStringArray(R.array.contEducationUrls)));
                for (int i = 0; i < countryListUrls.size(); i++) {
                    if (schoolCountryID == i) {
                        schoolCountry = countryListUrls.get(i);
                    }
                }
            }
        } else if (requestCode == REQUEST_SCHOOL_TYPE) {
            if (resultCode == RESULT_OK) {
                schoolType = intent.getIntExtra("schoolTypeID", -1);
                ArrayList<String> schoolTypeList = new ArrayList<>(Arrays.asList(activity.getResources().getStringArray(R.array.selSchoolNo)));
                for (int i = 0; i < schoolTypeList.size(); i++) {
                    if (schoolType - 1 == i) {
                        schoolTypeTextView.setText(schoolTypeList.get(i));
                    }
                }
            }
        } else if (requestCode == REQUEST_SCHOOL_SELECT) {
            if (resultCode == RESULT_OK) {
                activity.startActivity(new Intent(activity, MealActivity.class));
                activity.finish();
            }
        }
    }

    class CountryButtonViewHolder extends WearableRecyclerView.ViewHolder {
        TextView countryText;
        ImageView imageView;

        public CountryButtonViewHolder(View itemView) {
            super(itemView);
            countryText = itemView.findViewById(R.id.countryText);
            imageView = itemView.findViewById(R.id.countryImageView);
        }
    }

    class TypeButtonViewHolder extends WearableRecyclerView.ViewHolder {
        TextView typeText;
        ImageView imageView;

        public TypeButtonViewHolder(View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.schoolTypeText);
            imageView = itemView.findViewById(R.id.schoolTypeImageView);
        }
    }

    class SchoolEditTextViewHolder extends WearableRecyclerView.ViewHolder {
        EditText editText;

        public SchoolEditTextViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.schoolInput);
        }
    }

    class OpenOnPhoneButtonViewHolder extends WearableRecyclerView.ViewHolder {
        TextView typeText;
        ImageView imageView;

        public OpenOnPhoneButtonViewHolder(View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.openOnPhoneTitle);
            imageView = itemView.findViewById(R.id.openOnPhoneImageView);
        }
    }

    class SearchButtonViewHolder extends WearableRecyclerView.ViewHolder {
        ImageView imageView;

        public SearchButtonViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.schoolSearchImageView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {


        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.headerText);
            textView.setText("학교 선택");
        }
    }
}