package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

}
