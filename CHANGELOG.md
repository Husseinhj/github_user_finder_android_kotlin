# CHANGELOG

# v1.2.1 (vc-4)
- Inject all dependencies to guest classes.
- Fixed crash when converting date format with `YYYY` in Android below 6 and Java version 7.
- Refactor `ToolbarAppearance` class end extracted to another class as a `NavigationAppearance` class to manage the title and soft back button.

# v1.2 (vc-3)
- Improved Dark mode colors
- Improved animation of search results when users touch the screen
- Improved orientation of the application
- Maintain last application state when enabling Android dark mode or going to background
- Enhanced application performance

# v1.1 (vc-2)
- For each fragment, add a page title
- Refactor all fragments to manage toolbar states and remove EventBus from the project

# v1.0 (vc-1)
- Include an onboarding page
- Compatible with dark mode
- The ability to search for users
- View details of the user by clicking on the search result or deep-link (https://github.com/husseinhj)