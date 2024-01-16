package com.practoapp.controller;

import com.practoapp.entity.Review;
import com.practoapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @PostMapping
    public Review saveReview(@RequestBody Review review){
        return reviewService.createreview(review);
    }



}
