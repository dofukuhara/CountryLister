# CountryLister
![Version Name - 1.0.0](https://img.shields.io/badge/version%20name-1.0.0-blue.svg)
![Version Code - 1](https://img.shields.io/badge/version%20code-1-green.svg)

## Implementation
- Retrofit2 for network calls
- ViewModel and Coroutines to retrieve data from network layer and publish to view layer
- ViewBinding to get view reference
- RecyclerView to display the list of countries
- Pojo: receiving data from network as VO (Value Object) and converting it as a Model object to be consumed by the application

## Logic and Error Handling and Edge Cases
* In case of an error from the network call is encountered, **CountryListerFragment** will display an error message, as well as an option to retry the network call;
* In case of a success network call and the list of countries will be displayed by **CountryListerFragment** and there will be no option for end-user to perform a manual refresh;
* In the presentation of the data to the CountryCard, in case that `country name, region, code or capital` are empty, they will be replaced by a dash ("-"), just to not leave so many white space in the card;
* In case of a device orientation change, it will not re-trigger the network call, retaining the currently country list, as well as preserving the RecyclerView state/positioning;
* Although it was created a custom logging interface to be more robust, for the time being it was only implemented with default Android Log, but it can be improved in further releases.

## Project Configuration
* Target SDK Version `32`
* Compile SDK Version `32`
* Minimun SDK Version `21`
* Gradle Version `7.4`

## Module Structure
* `:app` : Main module of the app
* `common` : Cointains the utilities interfaces/classes used by this project (Either pattern, custom Logger, custom Exceptions and ModelMapper interface)
* `:designsystem` : Module that contains UI implementation of the project (color pallet, dimens and custom views)
* `:network` : Provides the Rest Client API
* `:testutils` : Contains utilities classes and objects used for Unit Testing.

## Unit Test
In this project, there are 3 Unit Test classes, all of them in `:app` module:
* `CountryCardFormatterTest` will validate if the **CountryCardFormatter** will properly format the data that will be displayed by CountryCard view
* `CountryModelMapperTest` will validate if the **CountryModelMapper** mapper will properly transform a `CountriesVo` into a `List<CountryModel>`
* `CountryListViewModelTest` will validate if **CountryListViewModel** will properly notify the View according to the responses received from **CountryRepository**

## 3rd-Party Libraries used in this Project:
* **Retrofit2** version `2.9.0` and **OKHTTP** version `5.0.0-alpha.10` for REST API calls;
* **Coroutines** version `1.6.4` to handle asynchronous calls;
* **MockK** version `1.13.3` and **JUnit4** for Unit Testing.

## Screenshots

| Main Screen | Error Screen |
| ----------- | ------------ | 
| <img src=https://raw.githubusercontent.com/dofukuhara/CountryLister/main/assets/CountryLister%20-%20Main%20Screen.png > | <img src=https://raw.githubusercontent.com/dofukuhara/CountryLister/main/assets/CountryLister%20-%20Error%20Screen.png > |
