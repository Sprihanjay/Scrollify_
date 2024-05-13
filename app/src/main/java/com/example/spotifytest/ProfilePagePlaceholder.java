package com.example.spotifytest;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfilePagePlaceholder extends AppCompatActivity
        implements AddFriendDialog.AddFriendDialogInterface, ChangePasswordDialog.ChangePasswordDialogInterface {

    String name, username, password, code, friends, invites;
    List<String> friendList;
    TextView nameTV;
    TextView usernameTV;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(ProfilePagePlaceholder.this);
    YourProfile thisProfile;



    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("username");
        }

        thisProfile = accountsDatabaseHandler.getAccount(username);

        name = thisProfile.getName();
        password = thisProfile.getPassword();
        code = thisProfile.getCode();
        friends = thisProfile.getFriends();
        invites = thisProfile.getInvites();

        nameTV = (TextView) findViewById(R.id.name_box);
        nameTV.setText(name);

        usernameTV = (TextView) findViewById(R.id.username_box);
        usernameTV.setText(username);

        ListView friendListView = findViewById(R.id.list_of_friends);

        if (friends != null && !friends.equals("friends,")) {
            friends = friends.substring(8);
            int index = 0;
            String addFriend;

            while (index != -1) {
                index = friends.indexOf(",");
                friends = friends.substring(index + 1);
                addFriend = friends.substring(0, index);
                friendList.add(addFriend);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.friends, R.id.friendUsername, friendList);
            friendListView.setAdapter(arrayAdapter);
        }

        Button goHome = (Button) findViewById(R.id.go_home);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, HomePage.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        Button add_friend_button = (Button) findViewById(R.id.addFriendButton);
        add_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialog customDialog = new AddFriendDialog();
                customDialog.show(getSupportFragmentManager(), "Add Friend");
            }
        });

        Button changePasswordButton = (Button) findViewById(R.id.editPassword);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog customDialog = new ChangePasswordDialog();
                customDialog.show(getSupportFragmentManager(), "Change Password");
            }
        });

        Button seeInvites = (Button) findViewById(R.id.see_invites_button);
        seeInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, SeeInvites.class);
                Bundle bundle = new Bundle();
                bundle.putString("invites", invites);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button logOut = (Button) findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, LoginPage.class);
                startActivity(intent);
            }
        });

        Button deleteAccount = (Button) findViewById(R.id.delete_account);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, LoginPage.class);
                startActivity(intent);
                accountsDatabaseHandler.deleteAccount(username);
            }
        });


    }

    public YourProfile getThisProfile() {
        return thisProfile;
    }


    @Override
    public void sendNewFriendInput(String friendUsername) {
        if (accountsDatabaseHandler.contains(friendUsername)) {
            accountsDatabaseHandler.addInvite(friendUsername, username);
            Toast.makeText(ProfilePagePlaceholder.this, "Successfully sent invite to " + friendUsername, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProfilePagePlaceholder.this, "That username does not exist!", Toast.LENGTH_SHORT).show();

        }

    }

    public void sendChangePasswordInputs(String oldPassword, String newPassword, String confirmPassword) {
        if (!getThisProfile().getPassword().equals(oldPassword)) {
            Toast.makeText(ProfilePagePlaceholder.this, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(ProfilePagePlaceholder.this, "Your New Passwords Didn't Match", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("else ran");
            Toast.makeText(ProfilePagePlaceholder.this, "Password successfully changed to " + newPassword, Toast.LENGTH_SHORT).show();
            accountsDatabaseHandler.changePassword(username, newPassword);
        }

    }


}


//
//package com.example.spotifytest;
//
//
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.content.Intent;
//
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProfilePagePlaceholder extends AppCompatActivity
//        implements AddFriendDialog.AddFriendDialogInterface, ChangePasswordDialog.ChangePasswordDialogInterface {
//
//    String name, username, password, code, friends, invites;
//    List<String> friendList;
//    TextView nameTV;
//    TextView usernameTV;
//    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(ProfilePagePlaceholder.this);
//    YourProfile thisProfile;
//
//
//
//
//
//    public void onCreate(Bundle saveInstanceState) {
//
//        super.onCreate(saveInstanceState);
//        setContentView(R.layout.profile_page);
//
//        Bundle bundle = getIntent().getExtras();
//        username = bundle.getString("username");
//
//        System.out.println("before " + username);
//        thisProfile = accountsDatabaseHandler.getAccount(username);
//        System.out.println("after");
//        name = thisProfile.getName();
//        password = thisProfile.getPassword();
//        code = thisProfile.getCode();
//        friends = thisProfile.getFriends();
//        invites = thisProfile.getInvites();
//
//        nameTV = (TextView) findViewById(R.id.name_box);
//        nameTV.setText(name);
//        usernameTV = (TextView) findViewById(R.id.username_box);
//        usernameTV.setText(username);
////
////        ListView friendsList = findViewById(R.id.list_of_friends);
//
//        Button goHome = (Button) findViewById(R.id.go_home);
//        goHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProfilePagePlaceholder.this, HomePage.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//        Button add_friend_button = (Button) findViewById(R.id.addFriendButton);
//        add_friend_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddFriendDialog customDialog = new AddFriendDialog();
//                customDialog.show(getSupportFragmentManager(), "Add Friend");
//            }
//        });
//
//        Button changePasswordButton = (Button) findViewById(R.id.editPassword);
//        changePasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChangePasswordDialog customDialog = new ChangePasswordDialog();
//                customDialog.show(getSupportFragmentManager(), "Change Password");
//            }
//        });
//
//
//
//
//    }
//
//    @Override
//    public void sendNewFriendInput(String friendUsername) {
//        if (accountsDatabaseHandler.contains(friendUsername)) {
//            accountsDatabaseHandler.addInvite(friendUsername, username);
//            Toast.makeText(ProfilePagePlaceholder.this, "Successfully sent invite to " + friendUsername, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(ProfilePagePlaceholder.this, "That username does not exist!", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
//
//        public YourProfile getThisProfile() {
//        return thisProfile;
//    }
//
//    public void sendChangePasswordInputs(String oldPassword, String newPassword, String confirmPassword) {
//        if (!getThisProfile().getPassword().equals(oldPassword)) {
//            Toast.makeText(ProfilePagePlaceholder.this, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
//        } else if (!newPassword.equals(confirmPassword)) {
//            Toast.makeText(ProfilePagePlaceholder.this, "Your New Passwords Didn't Match", Toast.LENGTH_SHORT).show();
//        } else {
//            System.out.println("else ran");
//            Toast.makeText(ProfilePagePlaceholder.this, "Password successfully changed to " + newPassword, Toast.LENGTH_SHORT).show();
//            accountsDatabaseHandler.changePassword(username, newPassword);
//        }
//    }
//
//
//}

