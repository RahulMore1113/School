package com.rahul.service;

import com.rahul.model.Profile;

import jakarta.servlet.http.HttpSession;

public interface IProfileService {

	public Profile getProfile(HttpSession session);

	public void updateProfile(Profile profile, HttpSession session);

}
