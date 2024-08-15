package com.example.company_service.messaging;

import com.example.company_service.dto.ReviewMessage;
import com.example.company_service.model.Company;
import com.example.company_service.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues="companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage) {
        companyService.updateCompanyRatings(reviewMessage);
    }
}
