# Image Search

## Demo Walkthrough

TBD!

## Stories implemented

- [x] User can enter a search query that will display a grid of image results from the Google Image API.

- [ ] User can click on "settings" which allows selection of advanced search options to filter results

- [ ] User can configure advanced search filters such as:

  - [ ] Size (small, medium, large, extra-large)
  - [ ] Color filter (black, blue, brown, gray, green, etc...)
  - [ ] Type (faces, photo, clip art, line art)
  - [ ] Site (espn.com)

- [ ] Subsequent searches will have any filters applied to the search results
- [x] User can tap on any image in results to see the image full-screen
- [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

## Optional stories:

- [x] **Advanced**: Robust error handling, check if internet is available, handle error cases, network failures
- [x] **Advanced**: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
- [ ] **Advanced**: User can share an image to their friends or email it to themselves
- [ ] **Advanced**: Replace Filter Settings Activity with a lightweight modal overlay
- [ ] **Advanced**: Improve the user interface and experiment with image assets and/or styling and coloring
- [x] **Bonus**: Use the StaggeredGridView to display improve the grid of image results
- [x] **Bonus**: User can zoom or pan images displayed in full-screen detail view
