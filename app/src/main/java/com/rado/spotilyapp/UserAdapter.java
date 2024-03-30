package com.rado.spotilyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rado.spotilyapp.R;
import com.rado.spotilyapp.User;
import com.rado.spotilyapp.ui.activities.UpdateUserActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;
    private DatabaseHelper dbHelper;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_info_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        // Bind user data to the ViewHolder
        holder.fullNameTextView.setText(user.getName());
        holder.emailView.setText(user.getEmail());
        // You can bind more user data here if needed
        // You can bind more user data here if needed

        // Set click listener for the delete button
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    User clickedUser = userList.get(clickedPosition);
                    // Get the email of the user to delete
                    String userEmail = clickedUser.getEmail();
                    // Delete the user from the database
                    boolean success = dbHelper.deleteUserByEmail(userEmail);
                    if (success) {
                        // Remove the user from the list and update the RecyclerView
                        userList.remove(clickedPosition);
                        notifyItemRemoved(clickedPosition);
                        Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Set click listener for edit button
        holder.editButton.setOnClickListener(view -> {
            // Perform action when edit button is clicked
            // You can navigate to EditUserActivity here
            Intent intent = new Intent(view.getContext(), UpdateUserActivity.class);
            // Pass the email of the us er to be edited to EditUserActivity using intent extras
            intent.putExtra("user_email", user.getEmail());
            view.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView fullNameTextView;
        TextView emailView;
        ImageButton buttonDelete;
        ImageButton editButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTextView = itemView.findViewById(R.id.fullNameTextView);
            emailView = itemView.findViewById(R.id.emailUser);
            editButton = itemView.findViewById(R.id.editButton);
            buttonDelete = itemView.findViewById(R.id.deleteButton);

        }
    }

    public void updateData(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged(); // Notify adapter of data changes
    }


}
