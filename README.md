# Hillfort Assignment 1 <br />
Mobile App Dev
<br />
<br />

## Features Accomplished
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

## Activities
<br />

### Splash
Shows WIT logo with some text. <br />

### Login <br />
Text fields for email and password. <br />
Two buttons - login and sign up. <br />
If the email AND password exist in the json file -> login <br />
else -> Toast displays "Login Failed" <br />


### SignUp <br />
Text fields where user enters name, email and password. <br />
This creates a new User object and appends data into the json file. <br />

### HillfortList <br />
When created, it will use the email address used to log in and retrieve the hillfort entries that is associated with that user. <br />

Buttons: <br />
- Logout -> goes back to login page <br />
- Settings -> opens Settings activity <br />
- Add -> opens Add activity <br />

### Hillfort <br />
Adds or edits hillfort entry <br />

### Settings <br />
Allows user to change name, email or password. <br />
Allows user to delete account. <br />
Shows amount of hillforts visisted out of unvisited. <br />

### Maps <br />
For setting location of an entry <br />
<br />


## Unimplemented
- Multiple Images <br />
- Additional notes <br />




