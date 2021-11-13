## Instructions

This part of the interview process allows us to learn more about your software engineering and web development skills. Below is a description of a CRUD API that manages products, keeps track of their pricing and their view counts. You are given a boilerplate application with parts that are incomplete or not working as expected. The task is to add features to the application, adapt some parts of it and come prepared at the next interview with suggestions on how to improve it.

The boilerplate application has some basic components set up: a Product model with a database connection and an empty controller. We would like you to do the following:
- Add an API to get a single product
- Finish the implementation for fetching the currency conversion

When a single product is requested, all fields of that product are returned and the view-count for that product is incremented. The request can optionally specify a currency, in which case the price should be converted to the requested currency before being returned. We need to support the following currencies:
*	USD (default)
*	CAD
*	EUR
*	GBP

The latest exchange rates are retrieved from the public API https://currencylayer.com/. Tests are optional but we would like to hear from you how would you design such tests at the interview.


## Changes
1. API's to get product by id, one to handle requests with optional currency and another to handle requests with currency.
2. Logic to increment product view count during get product call.
3. Logic to fetch currency exchange rates and convert the product price to the input currency during get product call.
4. To avoid chatty calls to external https://currencylayer.com/ for every get product request, the following improvements done
    1. A `quote` postgres table is created which contains the latest exchange rates for the supported currency.
    2. The `FxService` contains a scheduler which fetches the forex exchange rates at an interval and updates the `quote` table
    3. The `FxService` uses the `quote` tables to fetch the currency exchange rate.
    4. By doing so, the calls to convert the product price to given currency doesn't hit https://currencylayer.com/ for every request
5. Utils:
    1. Supported currencies as enums
    2. String to Enum converter helper
6. E2E tests targeting the product rest api's

#### Tests
<img width="414" alt="prodman_–_ProductEnd2EndTest_java__prodman_test_" src="https://user-images.githubusercontent.com/3944743/141608519-884c860f-367b-4fa3-96de-6dc5e773e528.png">


## Improvements
1. Tests for rest, service and repository packages
2. Decouple `ProductService` and `FxService`. Apply single responsibility principle. Converting product price to given currency should be outside of `ProductService`.
3. Tune the scheduler logic to fetch/update the forex exchange rates as needed.
4. Error handling, logging, metrics and observability.
5. Improve development experience by spinning up local docker container with postgres server, and connecting the application to it.
    1. Skip calling https://currencylayer.com/ during development by using a pre-loaded `quote` table.
