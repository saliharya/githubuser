# GitHub User App

## Features

### Main Features

1. **User Search**
   - Displays a list of user data retrieved from the API using RecyclerView, with at least avatar photo and username displayed.
   - User search functionality utilizing data from the API functions properly.
   - Users can view detailed pages of search results.

2. **User Detail**
   - Displays detailed information about a user including:
     - Avatar photo
     - Username
     - Name
     - Number of followers
     - Number of following
   - Includes fragments for the list of followers and following fetched from the API.
   - Utilizes Tab Layout and ViewPager for navigation between follower and following lists.

3. **Loading Indicators**
   - Includes loading indicators across all sections that fetch data from the API:
     - User data list
     - User detail
     - Following list
     - Follower list

### Additional Features

1. **Favorite User with Database**
   - Ability to add and remove users from the favorites list.
   - Includes a page displaying the list of favorite users.
   - Allows viewing detailed pages of favorite users.

2. **Theme Settings**
   - Implements a menu for changing the theme (light or dark) using key-value storage.
   - Ensures the selected theme persists even after closing and reopening the application.
   - Observes data changes and implements the theme on the first page.
   - Ensures all components and indicators remain visible when the theme changes (text and indicators are not obscured in dark mode).

## Implementation Details

- Base URL: `https://api.github.com/`
- API Key: Provided API_KEY
- Implemented Modularization, with favorites as a dynamic feature.
- Utilized Clean Architecture with distinct models for each layer.
- Implemented Dependency Injection using Koin.
- Utilized Reactive Programming with Flow.

### Additional Criteria Fulfillment

- Implemented a suitable application interface.
- Added additional features: searching and dark mode.
- Avoided repeating code from the module: Utilized Reactive Programming Flow and Dependency Injection with appropriate scopes.
- Few to no issues found in all aspects during Inspect Code.
- Added unit tests to the application.
- Implemented Continuous Integration with Unit Tests.
- Implemented additional security measures such as Crashlytics.
- Utilized Continuous Integration using Code Magic.

## Continuous Integration

Continuous Integration was implemented using Code Magic. Please refer to this [LINK](https://codemagic.io/app/65dddd2292db8ff2910fffa8/build/65e2b34570e42389ec4218eb).
