package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;


    public ActivityResponse trackActivity(ActivityRequest request)
    {
            Activity activity = Activity.builder()
                    .userId(request.getUserId())
                    .type(request.getType())
                    .duration(request.getDuration())
                    .caloriesBurned(request.getCaloriesBurned())
                    .startTime(request.getStartTime())
                    .additionalMetrics(request.getAdditionalMetrics())
                    .build();

         Activity savedActivity =   activityRepository.save(activity);
        System.out.println("Saved Activity: " + savedActivity);


        return mapToResponse(savedActivity);

    }

    private ActivityResponse mapToResponse(Activity activity)
    {
        ActivityResponse response = new ActivityResponse();

        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setStartTime(activity.getStartTime());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());

        return response;
    }

    public List<ActivityResponse>getUserActivities(String userId)
    {
        List<Activity> userActivities = activityRepository.findByUserId(userId);

        List<ActivityResponse> activities = userActivities
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return activities;
    }

    public ActivityResponse getActivity(String activityId)
    {
        Activity activity = activityRepository.findById(activityId).orElseThrow(()-> new RuntimeException("no such activity Exists"));
        return mapToResponse(activity);
    }
}


//  @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @PostConstruct
//    public void showConfigLocation() {
//        System.out.println("Config classpath check: "
//                + getClass().getClassLoader().getResource("application.properties"));
//    }
//
//    @PostConstruct
//    public void checkDb() {
//        System.out.println("Connected DB: " + mongoTemplate.getDb().getName());
//        System.out.println("CONFIG DB VALUE: " + db);
//    }
//
//
//    @Value("${spring.data.mongodb.database:NOT_FOUND}")
//    private String db;