# Hillfort Assignment 1 <br />
Mobile App Dev
<br />
<br />

## Features Accomplished
Assignment 1 and 2 <br />

- Splash Screen <br />
- Login/Logout/Sign Up <br />
- Settings - Change name, email and pass and delete user <br />
- Shows how many visited out of total <br />
- Lists hillfort that user has visited <br />
- Add new hilfort entry <br />
- Delete existing hillfort entry <br />
- Google Maps for picking location <br />

<br />
<br />

Assignment 2 <br />

- Login/Logout/Sign Up (using Firebase Authentication)
- Settings (changed to save in Firebase)
- Add favourites and rating value in add/edit hillfort
- View all favourites in HillfortListActivity
- View location of all hillforts that have location set in map (accessible in HillfortListActivity)
- Search hillfort by title - returns hillforts that contains query in title
- Landscape view compatible

<br />
<br />

## Activities
<br />

### Splash
Shows WIT logo with some text. <br />

### Login <br />
Text fields for email and password. <br />
Two buttons - login and sign up. <br />
If the email AND password is correct in Firebase -> login <br />
else -> Toast displays "Login Failed" <br />


### SignUp <br />
Text fields where user enters name, email and password. <br />
This creates a new User object and appends data into the json file (only stores email and name). <br />
Creates user entry in Firebase using email and password provided by user. <br />

### HillfortList <br />
When created, it will use the email address used to log in and retrieve the hillfort entries that is associated with that user. <br />

Buttons: <br />
- Logout -> goes back to login page <br />
- Settings -> opens Settings activity <br />
- Add -> opens Add activity <br />
- Map -> opens map of all hillforts <br />
- Search -> opens search activity <br />

### Hillfort <br />
Adds or edits hillfort entry <br />

### Settings <br />
Allows user to change name, email or password. <br />
Allows user to delete account. <br />
Shows amount of hillforts visisted out of unvisited. <br />

### Maps <br />
For setting location of an entry <br />
<br />

### HillfortMap <br />
A Google Maps actvity that shows the location of all hillforts that have the location set. <br />
It will not display hillforts that do not have location set.
<br />

![Map of Hillforts](/images/maphillforts.png)

<br />

### Search Actvity <br />
Search page enables querying hillfort titles. If the string queried is contaied in the title of hillforts, those hillforts will be displayed.
<br />

![Searching for Hillfort](/images/search.png)

<br />

## Unimplemented
Assignment 1 <br />
- Multiple Images <br />
- Additional notes <br />

<br />
<br />

Assigment 2 <br />
- Firebase Database storage
- Swipe support
- Bottom navigation
- Images from camera
- Current location




