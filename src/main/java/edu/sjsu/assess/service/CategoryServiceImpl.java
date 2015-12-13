package edu.sjsu.assess.service;

import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.JobCodeSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bjoshi on 4/2/15.
 */
@Service
public class CategoryServiceImpl
{
    @Autowired
    private CategoryDAOImpl categoryDAOImpl;

    @Autowired
    private JobCodeServiceImpl jobcodeService;

    public List<Category> getAllPredefinedCategories() {

        List<Category> categoryList = null;
        try {
            categoryList = categoryDAOImpl.getAllPredefinedCategories();
        }
        catch (DAOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return categoryList;
    }

    public List<Category> getCategoriesForJobCode(int jobcodeId, Integer parentCategoryId)
            throws JobCodeException
    {
        Set<Category> categoryList = new HashSet<>();
        JobCodeSearchParams searchParams = new JobCodeSearchParams();
        searchParams.setId(jobcodeId);
        JobCode code = jobcodeService.getJobCode(searchParams);
        Category parentCategory = null;

        if(parentCategoryId != null) {
            parentCategory = new Category();
            parentCategory.setParentCategoryID(parentCategoryId);
        }

        jobcodeService.getAllCategoriesOfJobCode(code, parentCategory);

        if(code.getFirstLevelCategories()!=null) {
            for(Category category: code.getFirstLevelCategories()) {
                categoryList.add(category);
                if(category.getSubCategories()!=null) {
                    categoryList.addAll(category.getSubCategories());
                }
            }
        }
        return new ArrayList<>(categoryList);
    }

    public Category getCategoryById(int categoryId)
            throws DAOException
    {
        return categoryDAOImpl.getCategoryByID(categoryId);
    }

}
