package com.matt_adshead.mubaloostaff.view.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matt_adshead.mubaloostaff.R;
import com.matt_adshead.mubaloostaff.model.Employee;
import com.matt_adshead.mubaloostaff.view.listener.OnItemClickListener;

import java.util.List;

import static com.matt_adshead.mubaloostaff.model.Employee.Role.CEO;

/**
 * RecyclerView.Adapter for EmployeeList.
 *
 * @author Matt
 * @date 05/03/2018
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    /**
     * Calling context.
     */
    private Context        context;

    /**
     * List of employees to represent in the RecyclerView.
     */
    @Nullable
    private List<Employee> employeeList;

    /**
     * Item click listener, called when an item is clicked.
     */
    @Nullable
    private OnItemClickListener<Employee> itemClickListener;

    /**
     * Constructor.
     *
     * @param context           Calling context.
     * @param employeeList      List of employees.
     * @param itemClickListener Item click listener.
     */
    public EmployeeListAdapter(Context context, @Nullable List<Employee> employeeList,
                               @Nullable OnItemClickListener<Employee> itemClickListener) {
        this.context      = context;
        this.employeeList = employeeList;
        this.itemClickListener = itemClickListener;
    }

    /**
     * Shortcut constructor signature for omitting employeeList at construct time.
     *
     * @param context           Calling context.
     * @param itemClickListener Item click listener.
     */
    public EmployeeListAdapter(Context context,
                               @Nullable OnItemClickListener<Employee> itemClickListener) {

        this(context, null, itemClickListener);
    }

    /**
     * Shortcut constructor signature for omitting employeeList and itemClickListener at
     * construct time.
     *
     * @param context Calling context.
     */
    public EmployeeListAdapter(Context context) {
        this(context, null, null);
    }

    /**
     * @param employeeList Employee list.
     */
    public void setEmployeeList(@Nullable List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    /**
     * @param itemClickListener Item click listener.
     */
    public void setItemClickListener(@Nullable OnItemClickListener<Employee> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // ********************************************************************************************
    // * RecyclerView.Adapter Methods
    // ********************************************************************************************

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.employee_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);

        Glide.with(context)
             .load(employee.getProfileImageUrl())
             .into(holder.profileImage);

        holder.nameText.setText(employee.getFirstName());
        holder.roleText.setText(employee.getRole());


        // todo A nice idea but for some reason as you scroll around this gets hit when
        //      employee.isTeamLead() should resolve to false. Can't figure out the reason rn.
        //        if (employee.isTeamLead()) {
        //            setHolderBackgroundColour(holder, R.color.color_primary);
        //            setTextColourWhite(holder);
        //        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(employee);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList != null ? employeeList.size() : 0;
    }

    // ********************************************************************************************
    // * Utility Methods
    // ********************************************************************************************

    /**
     * Set the background colour of a ViewHolder.
     *
     * @param holder ViewHolder to apply transformation to.
     * @param colour Colour resource ID.
     */
    private void setHolderBackgroundColour(final ViewHolder holder, final int colour) {
        holder.itemView.setBackgroundColor(
                ResourcesCompat.getColor(
                        context.getResources(),
                        colour,
                        null
                )
        );
    }

    /**
     * Switch the TextViews to have white text color.
     * Should be used in conjunction with {@link #setTextColourWhite(ViewHolder)}}.
     *
     * @param holder ViewHolder to apply transformation to.
     */
    private void setTextColourWhite(final ViewHolder holder) {
        holder.nameText.setTextColor(
                ResourcesCompat.getColor(
                        context.getResources(),
                        android.R.color.white,
                        null
                )
        );

        holder.roleText.setTextColor(
                ResourcesCompat.getColor(
                        context.getResources(),
                        android.R.color.white,
                        null
                )
        );
    }

    /**
     * Inner class defining the ViewHolder for a list item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        // ****************************************************************************************
        // * Views
        // ****************************************************************************************

        public View      container;
        public ImageView profileImage;
        public TextView nameText, roleText;

        /**
         * Constructor.
         *
         * @param itemView Root View.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            container    = itemView.findViewById(R.id.employee_container);
            profileImage = itemView.findViewById(R.id.employee_profile_image);
            nameText     = itemView.findViewById(R.id.employee_name);
            roleText     = itemView.findViewById(R.id.employee_role);

            // Set the placeholder drawable, will be replaced by glide when Employee is bound.
            profileImage.setImageDrawable(new ColorDrawable(
                    ResourcesCompat.getColor(
                            itemView.getResources(),
                            R.color.color_accent,
                            null
                    )
            ));
        }
    }
}
