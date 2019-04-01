## Requirements Document 
####Group RHO, Word Sudoku, CMPT 276

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

**User Story 1:** 
As a language learner, I want to be able to learn a new language through playing my favourite game, Sudoku.

*Scenario 1*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "English -> French" mode and specifying the grid size as "DEFAULT: 9x9 grid"
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with a set of English words and a menu with the corresponding words in French

*Scenario 2*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "French -> English" mode and specifying the grid size  as "DEFAULT: 9x9 grid"
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with a set of French words and a menu with the corresponding words in English

*Scenario 3*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "French -> English" mode and specifying the grid size as "DEFAULT: 9x9 grid" and enabling listening comprehension mode
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with numbers ranging from 1 to 9 and a menu with words in English

*Scenario 4*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "English -> French" mode and specifying the grid size as "DEFAULT: 9x9 grid" and enabling listening comprehension mode
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with numbers ranging from 1 to 9 and a menu with words in French 

------------------------------------------------------------------------------------------------------------------------
**User Story 2:** 
As a language learner, I want to be able to peek at the correct translation of a word, so that I can try to remember it in filling out the puzzle.

*Scenario 1:*
* Given: that the user is filling in the sudoku grid in English -> French mode with listening comprehension enabled 
	 AND the user has tapped a pre-filled cell containing the digit 4 (as an example) once 
	 AND the user hears the word "vert" read out and pronounced in French
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the digit 4) again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "vert"

*Scenario 2:*
* Given: that the user is filling in the sudoku grid in French -> English mode with listening comprehension enabled 
	 AND that the user has tapped a pre-filled cell containing the digit 4 (as an example) once 
	 AND that the user hears the word "vert" read out and pronounced in French
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the digit 4) again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "green"

*Scenario 3:*
* Given: that the user is filling in the sudoku grid in English -> French mode (with listening comprehension mode disabled)
	 AND that the user has tapped a pre-filled cell containing the word "green" (as an example) once 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the word "green") again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "vert"

*Scenario 4:*
* Given: that the user is filling in the sudoku grid in French -> English mode (with listening comprehension disabled)
	 AND that the user has tapped a pre-filled cell containing the word "vert" (as an example) once 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the word "vert") again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "green"

------------------------------------------------------------------------------------------------------------------------
**User Story 3:** 
As a language learner, I want to be able to play different modes (English -> French and French -> English) to support my language learning. 

*Scenario 1*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects English -> French mode (listening comprehension mode disabled)
* Then: the user sees a sudoku grid with some prefilled cells containing words in English and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in French

*Scenario 2*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects French -> English mode (listening comprehension mode disabled)
* Then: the user sees a sudoku grid with some prefilled cells containing words in French and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in English

*Scenario 3*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects English -> French mode with listening comprehension mode enabled
* Then: the user sees a sudoku grid with some prefilled cells containing digits in the range 1 to 9 and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in French

*Scenario 4*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects French -> English mode with listening comprehension mode enabled
* Then: the user sees a sudoku grid with some prefilled cells containing digits in the range 1 to 9 and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in English

-------------------------------------------------------------------------------------------------------------------

**User Story 4:**
As a language learner and/or new user, I want to be able to refresh my understanding about the rules of the game and learn how to navigate the application.

*Scenario 1* 
* Given: that the user has launched the application and is viewing the first/launch page of the application
* When: the user presses the "INSTRUCTIONS" button
* Then: the user is directed to a page with instructions where the user can learn the rules of the game

*Scenario 2* 
* Given: that the user is currently viewing the 'INSTRUCTIONS' page of the application
* When: the user presses the back button while on the 'INSTRUCTIONS' page
* Then: the user is redirected to the start page and can proceed to select 'PLAY'

-------------------------------------------------------------------------------------------------------------------

**User Story 5:**
As a language learner, I want to be able to verify that my solution to the sudoku puzzle is correct.

*Scenario 1*
* Given: that the user has filled in all empty cells of the grid with words from the menu
	 AND that the user's solution to the puzzle is correct
* When: the user presses the "CHECK YOUR ANSWERS" button 
* Then: a pop-up message (with the exclamation "You did it!") appears on screen, notifying the user that his/her solution is correct

*Scenario 2*
* Given: that the user has filled in all empty cells of the grid with words from the menu
	 AND that the user's solution to the puzzle is incorrect
* When: the user presses the "CHECK YOUR ANSWERS" button 
* Then: a pop-up message ("Try again!") appears on screen, notifying the user that his/her present solution is incorrect
        AND the user can change their answer and continue inputting words into cells of the grid to find the correct solution

*Scenario 3*
* Given: that the user has not yet filled in all empty cells of the grid with words from the menu 
         (therefore, rendering the solution incomplete/incorrect)
* When: the user presses the "CHECK YOUR ANSWERS" button 
* Then: a pop-up message ("Try again!") appears on screen, notifying the user that his/her present solution is incorrect
        AND the user can change their answer and continue inputting words into cells of the grid to find the correct solution

-------------------------------------------------------------------------------

**User Story 6:**
As a language learner, I want the application interface to be appealing, organized, practical and easy to use.

*Scenario 1*
* Given: that the user has downloaded Wudoku on their device
* When: the user launches the app
* Then: the first/launch page appears on the screen 
	AND the user can opt to "PLAY" and proceed with setting up a puzzle of Wudoku or the user can choose to view the instructions for the game.

*Scenario 2*
* Given: that the user has launched the application 
* When: the user is setting up and initiating his/her puzzle
* Then: the user sees that the colour scheme for the different page backgrounds of the application is consistent

*Scenario 3*
* Given: that the user has launched the application 
* When: the user selects "INSTRUCTIONS" on the launch/start page of the application
* Then: the user is directed to a page with instructions about how to play Word Sudoku and how to navigate the application (how to set up games and how to access hints)


-------------------------------------------------------------------------------

**User Story 7:**
As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.

*Scenario 1*
* Given: that the user launches the application on a tablet
* When: the user is currently solving a puzzle (filling empty cells of the grid)
* Then: the menu (consisting of buttons) and the grid are adjusted to dynamically fit the screen of the tablet device 
        AND words are easy to read
	AND the sudoku grid (regardless of grid size) spans the entire screen width in portrait mode, and the entire screen height in landscape mode

-------------------------------------------------------------------------------

**User Story 8:**
As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font than standard mode.

*Scenario 1*
* Given: that the user is currently solving a puzzle (filling empty cells of a grid)
* When: the user rotates the device, changing the orientation of the device from portrait to landscape (thereby changing the device configuration)
* Then: the state of the game is preserved 
        AND game data (such as the words inputted into grid cells by the user) is not lost

*Scenario 2*
* Given: that the user is currently solving a puzzle (filling empty cells of a grid)
* When: the user rotates the device, changing the orientation of the device from landscape to portrait (thereby changing the device configuration)
* Then: the state of the game is preserved 
        AND game data (such as the words inputted into grid cells by the user) is not lost

--------------------------------------------------------------------------------

**User Story 9:** 
As a teacher, I want to specify a list of word pairs for my students to practice this week.

*Scenario 1*
* Given: that the user selects the "PLAY" option from the launch page
         AND that the user is directed to the 'Game Mode' page 
	 AND that the user has a CSV file with a list of word pairs on his/her device (assigned by the teacher) 
* When: the user selects the "GENERATE YOUR OWN PUZZLE" option 
* Then: the user can upload a CSV file in the format specified on the screen

*Scenario 2*
* Given: that the user selects the "PLAY" option from the launch page
         AND that the user is redirected to the 'Game Mode' page 
	 AND that the user has a CSV file with a list of word pairs on his/her device (assigned by the teacher)
	 AND that the user has selected the "GENERATE YOUR OWN PUZZLE" option
* When: the user uploads a CSV file in the format specified on the screen
* Then: the user is directed to the "Select Language Mode" page and can proceed to select a language mode, enable or disable Listening Comprehension Mode and start a randomly generated puzzle created from the set of words provided/loaded 

--------------------------------------------------------------------------------

**User Story 10:** 
As a student working with a textbook, I want to load pairs of words to practice from each chapter of the book.

*Scenario 1*
* Given: that the user selects the "PLAY" option from the launch page
         AND the user is redirected to the 'Game Mode' page 
	 AND the user has a CSV file with a list of word pairs on his/her device  
* When: the user selects the "GENERATE YOUR OWN PUZZLE" option 
* Then: the user can upload a CSV file containing pairs of words in the format specified on the screen

*Scenario 2*
* Given: that the user selects the "PLAY" option from the launch page
         AND the user is redirected to the 'Game Mode' page 
	 AND the user has a CSV file with a list of word pairs on his/her device 
	 AND the user has selected the "GENERATE YOUR OWN PUZZLE" option
* When: the user uploads a CSV file (containing pairs of words) in the format specified on the screen
* Then: the user is directed to the "Select Language Mode" page and can proceed to select a language mode, enable or disable Listening Comprehension Mode and start a randomly generated puzzle created from the set of words provided/loaded 

--------------------------------------------------------------------------------

**User Story 11:** 
As a student who wants to practice my understanding of spoken words in the language that I am learning, I want a listening comprehension mode. In this mode, numbers will appear in the pre-filled cells and the corresponding word in the language that I am learning will be read out to me when I press the number.

*Scenario 1*
* Given: that the user has enabled listening comprehension mode
* When: the user initiates a new puzzle
* Then: the user sees a standard Sudoku grid with some prefilled cells showing digits in the range 1..9 and all other cells empty

*Scenario 2*
* Given: that the user is filling in the grid in listening comprehension mode,
         AND that the grid includes a cell with the prefilled digit 4
	 AND that word pair 4 is (green, vert)
* When: the user presses the prefilled cell having the digit 4
* Then: the user hears the word "vert" read out and pronounced in French

*Scenario 3*
* Given: that the user is filling in the grid in listening comprehension mode,
	 AND that the grid includes a cell with the prefilled digit 4
	 AND that word pair 4 is (green, vert)
* When: the user selects a non-prefilled cell to enter the word "green"
* Then: the word "green" appears in the menu (list of words that may be selected), but not in the fourth position

*Scenario 4*
* Given: that the user is filling in the grid in listening comprehension mode
* When: the user presses a cell and hears the word "vert"
* Then: the user does not see the word "vert" anywhere on the game grid (unless the user is unlocking a hint by tapping the cell twice)

*Scenario 5*
* Given: that the user is filling in the grid in English -> French mode with listening comprehension enabled 
	 AND that the user presses a cell and hears the word "vert" 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user taps on the same cell again to unlock a hint
* Then: the word "vert" will be pronounced again and the word "vert" will appear on the screen briefly 

*Scenario 6*
* Given: that the user is filling in the grid in French -> English mode with listening comprehension enabled 
	 AND that the user presses a cell and hears the word "vert" 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user taps on the same cell again to unlock a hint
* Then: the word "vert" will be pronounced again and the word "green" will appear on the screen briefly

--------------------------------------------------------------------------------

**User Story 12:** 
As a language learner, I want to be able to enter words into the sudoku grid as I play and practice listening comprehension and recognition without having to worry too much about spelling mistakes and language keyboards. 

*Scenario 1*
* Given: that the user has initiated a puzzle 
	 AND that a menu with the list of words that can be entered into the grid is located on the screen
* When: the user clicks on a fillable cell (i.e. not a pre-filled cell) in the grid and subsequently clicks on a word from the menu
* Then: the specified word will appear in the specified cell in the sudoku grid (No typing of words is required for entry/placement of words into the grid by the user)
	AND the cell will be shaded green to distinguish cells with user input from cells that are part of the pre-filled configuration

--------------------------------------------------------------------------------

**User Story 13:** 
As a language learner, I want to be able to identify which cells I have recently manipulated (by entering words) so far so I can visually track my recent progress and easily distinguish between words I have recently entered and words that are part of the pre-filled configuration. 

*Scenario 1*
* Given: that the user has initiated a puzzle  
* When: the user presses an empty cell of the grid to enter a word from the menu into the cell
* Then: the selected cell is shaded green and word that was selected from the word menu will be placed in the grid cell most recently tapped

--------------------------------------------------------------------------------

**User Story 14:**
As a language learner, I want the sudoku grid to have a random pre-filled configuration (i.e. be pre-filled with words or numbers at random locations) so that I can stay engaged every time I practice new words with a sudoku puzzle.

*Scenario 1*
* Given: that the user launches the app and selects "PLAY"
* When: the user initiates a puzzle
* Then: the application's random sudoku generator generates a different/random sudoku puzzle (i.e. configuration of pre-filled cells in puzzle is randomized) 

--------------------------------------------------------------------------------

**User Story 15:**
As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3).


*Scenario 1*
* Given: that the user has selected "PLAY" 
	 AND the user is directed to the "Game Mode" page of the application 
* When: the user selects the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
* Then: the user is directed to the "Select Language Mode" of the application and sees that there is a drop-down menu which contains the following options ("DEFAULT: 9x9 grid", "4x4 grid", "6x6 grid", "12x12 grid")


*Scenario 2*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" page of the application
	 AND that the user has pressed on the drop-down menu on the "Select Language Mode" page
* When: the user selects the "4x4 grid" option from the drop-down menu and initiates the puzzle by selecting a mode (i.e. English -> French)
* Then: the user sees a sudoku grid of size 4x4 (partially pre-filled with English words) and 4 words in the menu (in French)

*Scenario 3*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" page of the application
	 AND that the user has pressed on the drop-down menu on the "Select Language Mode" page
* When: the user selects the "6x6 grid" option from the drop-down menu and initiates the puzzle by selecting a mode (i.e. English -> French)
* Then: the user sees a sudoku grid of size 6x6 (partially pre-filled with English words) with subgrids of size 2x3 and a menu consisting of 6 words (in French)


--------------------------------------------------------------------------------

**User Story 16:**
As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4).

*Scenario 1*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" of the application
* When: the user is setting up the language mode and enabling or disabling listening comprehension mode
* Then: the user sees that there is a drop-down menu which contains the following options ("DEFAULT: 9x9 grid", "4x4 grid", "6x6 grid", "12x12 grid")

*Scenario 2*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" of the application
	 AND that the user has pressed on the drop-down menu on the "Select Language Mode" page
* When: the user selects the "12x12 grid" option from the drop-down menu and initiates the puzzle by selecting a mode (i.e. English -> French)
* Then: the user sees a sudoku grid of size 12x12 (partially pre-filled with English words) with subgrids of size 3x4 and a menu consisting of 12 words (in French)

--------------------------------------------------------------------------------

**User Story 17:**
As a user, I want to be able to continue a puzzle where I left off- if I have not yet completed the puzzle. 

*Scenario 1*
* Given: that the user has selected to "PLAY" 
	 AND that the user has initiated a puzzle
* When: the user leaves the page
* Then: the current state of the game (including user filled grid cells) is automatically saved and can be accessed again by selecting "Continue" on the 'Game Mode' page of the application

*Scenario 2*
* Given: that the user has selected to "PLAY" 
	 AND is directed to the "Game Mode" page of the application
* When: the user selects "Continue"
* Then: the user can continue the most recent game that was saved 

--------------------------------------------------------------------------------
**User Story 18:**
As a user, I want the application's icon to be distinguishable from other applications on my device. As a potential user searching the app store, I would like to be able to get a sense of what the app is about based on the icon. 

*Scenario 1*
* Given: that the user has downloaded Wudoku 
* When: the user is looking for our application amongst all the other apps on their device
* Then: the user can recognize our application by its distinctive app icon 

*Scenario 2*
* Given: that the user has not yet downloaded Wudoku
* When: the user is looking for the Wudoku application in the app store
* Then: the user can look at the application icon to get a sense of what the Wudoku application is about

--------------------------------------------------------------------------------

**User Story 19:**
As a user, I want to have a weighted score to keep track of my progress on completed Wudoku puzzles

*Scenario 1*
* Given: that the user has initiated a 9x9 puzzle 
* When: the user completes a 9x9 puzzle
* Then: the score is increased by 9 points 

*Scenario 2*
* Given: that the user has initiated a 4x4 puzzle 
* When: the user completes a 4x4 puzzle
* Then: the score is increased by 4 points 

*Scenario 3*
* Given: that the user has initiated a 6x6 puzzle 
* When: the user completes a 6x6 puzzle
* Then: the score is increased by 6 points 

*Scenario 4*
* Given: that the user has initiated a 12x12 puzzle 
* When: the user completes a 12x12 puzzle
* Then: the score is increased by 12 points 

---------------------------------------------------------------------------------
**User Story 20:**
As a user, I want to be able to be customize the colour scheme of the application

*Scenario 1*
* Given: that the user has launched the application
* When: the user selects a colour scheme from a menu on the launch page
* Then: the application's colour scheme (background) will be changed to the selected colour scheme

*Scenario 2*
* Given: that the user has launched the application
* When: the user selects the light mode option for the colour scheme
* Then: the application's colour scheme (background) will be adapted for light mode

*Scenario 3*
* Given: that the user has launched the application
* When: the user selects the dark mode option for the colour scheme 
* Then: the application's colour scheme (background) will be adapted for dark mode

--------------------------------------------------------------------------------

**User Story 21:**
As a user, I want to be able to spread the word about this application (by sharing my current score) to my friends so they can also begin learning a new language in a fun way!

*Scenario 1*
* Given: that the user has downloaded Wudoku on their device 
* When:  the user has pressed the "Share" button on the front/launch page of the application
* Then: the user can share his/her score and spread the word to his/her friends about Wudoku

--------------------------------------------------------------------------------

**User Story 22:**
As a user, I want to keep track of how much time I have spent in solving a puzzle. 

*Scenario 1*
* Given: that the user has selected "PLAY"
* When:  the user initiated a puzzle 
* Then: the user sees a timer and can see how long they have been working on the current puzzle 

--------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------

**User Story 1:** 
As a language learner, I want to be able to learn a new language through playing my favourite game, Sudoku.

*Scenario 1*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "English -> French" mode and specifying the grid size as "DEFAULT: 9x9 grid"
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with a set of English words and a menu with the corresponding words in French

*Scenario 2*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "French -> English" mode and specifying the grid size  as "DEFAULT: 9x9 grid"
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with a set of French words and a menu with the corresponding words in English

*Scenario 3*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "French -> English" mode and specifying the grid size as "DEFAULT: 9x9 grid" and enabling listening comprehension mode
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with numbers ranging from 1 to 9 and a menu with words in English

*Scenario 4*
* Given: that the user has launched the application
* When: the user initiates the puzzle by selecting "English -> French" mode and specifying the grid size as "DEFAULT: 9x9 grid" and enabling listening comprehension mode
* Then: the user sees a sudoku grid of the specified size that is partially pre-filled with numbers ranging from 1 to 9 and a menu with words in French 

------------------------------------------------------------------------------------------------------------------------
**User Story 2:** 
As a language learner, I want to be able to peek at the correct translation of a word, so that I can try to remember it in filling out the puzzle.


*Scenario 1:*
* Given: that the user is filling in the sudoku grid in English -> French mode with listening comprehension enabled 
	 AND the user has tapped a pre-filled cell containing the digit 4 (as an example) once 
	 AND the user hears the word "vert" read out and pronounced in French
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the digit 4) again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "vert"



*Scenario 2:*
* Given: that the user is filling in the sudoku grid in French -> English mode with listening comprehension enabled 
	 AND that the user has tapped a pre-filled cell containing the digit 4 (as an example) once 
	 AND that the user hears the word "vert" read out and pronounced in French
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the digit 4) again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "green"


*Scenario 3:*
* Given: that the user is filling in the sudoku grid in English -> French mode (with listening comprehension mode disabled)
	 AND that the user has tapped a pre-filled cell containing the word "green" (as an example) once 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the word "green") again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "vert"


*Scenario 4:*
* Given: that the user is filling in the sudoku grid in French -> English mode (with listening comprehension disabled)
	 AND that the user has tapped a pre-filled cell containing the word "vert" (as an example) once 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user presses on the same pre-filled cell (which contains the word "vert") again
* Then: a hint (in the form of a pop-up message) briefly appears on the screen and the message contains the word "green"

------------------------------------------------------------------------------------------------------------------------
**User Story 3:** 
As a language learner, I want to be able to play different modes (English -> French and French -> English) to support my language learning. 

*Scenario 1*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects English -> French mode (listening comprehension mode disabled)
* Then: the user sees a sudoku grid with some prefilled cells containing words in English and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in French

*Scenario 2*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects French -> English mode (listening comprehension mode disabled)
* Then: the user sees a sudoku grid with some prefilled cells containing words in French and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in English

*Scenario 3*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects English -> French mode with listening comprehension mode enabled
* Then: the user sees a sudoku grid with some prefilled cells containing digits in the range 1 to 9 and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in French

*Scenario 4*
* Given: the user has selected "PLAY" on the first page of the application
	 AND that the user has been directed to select Game Mode and selected to play a "Default Puzzle"
	 AND that the user is directed to the Select Language Mode page 
* When: the user selects French -> English mode with listening comprehension mode enabled
* Then: the user sees a sudoku grid with some prefilled cells containing digits in the range 1 to 9 and all other cells empty
	AND the user also sees a menu (consisting of buttons) on the screen containing the corresponding words in English

-------------------------------------------------------------------------------------------------------------------

**User Story 4:**
As a language learner and/or new user, I want to be able to refresh my understanding about the rules of the game and learn how to navigate the application.

*Scenario 1* 
* Given: that the user has launched the application and is viewing the first/launch page of the application
* When: the user presses the "INSTRUCTIONS" button
* Then: the user is directed to a page with instructions where the user can learn the rules of the game

*Scenario 2* 
* Given: that the user is currently viewing the 'INSTRUCTIONS' page of the application
* When: the user presses the back button while on the 'INSTRUCTIONS' page
* Then: the user is redirected to the start page and can proceed to select 'PLAY'

-------------------------------------------------------------------------------------------------------------------

**User Story 5:**
As a language learner, I want to be able to verify that my solution to the sudoku puzzle is correct.

*Scenario 1*
* Given: that the user has filled in all empty cells of the grid with words from the menu
	 AND that the user's solution to the puzzle is correct
* When: the user presses the "CHECK YOUR ANSWERS" button 
* Then: a pop-up message (with the exclamation "You did it!") appears on screen, notifying the user that his/her solution is correct

*Scenario 2*
* Given: that the user has filled in all empty cells of the grid with words from the menu
	 AND that the user's solution to the puzzle is incorrect
* When: the user presses the "CHECK YOUR ANSWERS" button 
* Then: a pop-up message ("Try again!") appears on screen, notifying the user that his/her present solution is incorrect
        AND the user can change their answer and continue inputting words into cells of the grid to find the correct solution

*Scenario 3*
* Given: that the user has not yet filled in all empty cells of the grid with words from the menu 
         (therefore, rendering the solution incomplete/incorrect)
* When: the user presses the "CHECK YOUR ANSWERS" button 
* Then: a pop-up message ("Try again!") appears on screen, notifying the user that his/her present solution is incorrect
        AND the user can change their answer and continue inputting words into cells of the grid to find the correct solution

-------------------------------------------------------------------------------

**User Story 6:**
As a language learner, I want the application interface to be appealing, organized, practical and easy to use.

*Scenario 1*
* Given: that the user has downloaded Wudoku on their device
* When: the user launches the app
* Then: the first/launch page appears on the screen 
	AND the user can opt to "PLAY" and proceed with setting up a puzzle of Wudoku or the user can choose to view the instructions for the game.

*Scenario 2*
* Given: that the user has launched the application 
* When: the user is setting up and initiating his/her puzzle
* Then: the user sees that the colour scheme for the different page backgrounds of the application is consistent

*Scenario 3*
* Given: that the user has launched the application 
* When: the user selects "INSTRUCTIONS" on the launch/start page of the application
* Then: the user is directed to a page with instructions about how to play Word Sudoku and how to navigate the application (how to set up games and how to access hints)


-------------------------------------------------------------------------------

**User Story 7:**
As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.

*Scenario 1*
* Given: that the user launches the application on a tablet
* When: the user is currently solving a puzzle (filling empty cells of the grid)
* Then: the menu (consisting of buttons) and the grid are adjusted to dynamically fit the screen of the tablet device 
        AND words are easy to read
	AND the sudoku grid (regardless of grid size) spans the entire screen width in portrait mode, and the entire screen height in landscape mode

-------------------------------------------------------------------------------

**User Story 8:**
As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font than standard mode.

*Scenario 1*
* Given: that the user is currently solving a puzzle (filling empty cells of a grid)
* When: the user rotates the device, changing the orientation of the device from portrait to landscape (thereby changing the device configuration)
* Then: the state of the game is preserved 
        AND game data (such as the words inputted into grid cells by the user) is not lost

*Scenario 2*
* Given: that the user is currently solving a puzzle (filling empty cells of a grid)
* When: the user rotates the device, changing the orientation of the device from landscape to portrait (thereby changing the device configuration)
* Then: the state of the game is preserved 
        AND game data (such as the words inputted into grid cells by the user) is not lost

--------------------------------------------------------------------------------

**User Story 9:** 
As a teacher, I want to specify a list of word pairs for my students to practice this week.

*Scenario 1*
* Given: that the user selects the "PLAY" option from the launch page
         AND that the user is directed to the 'Game Mode' page 
	 AND that the user has a CSV file with a list of word pairs on his/her device (assigned by the teacher) 
* When: the user selects the "GENERATE YOUR OWN PUZZLE" option 
* Then: the user can upload a CSV file in the format specified on the screen

*Scenario 2*
* Given: that the user selects the "PLAY" option from the launch page
         AND that the user is redirected to the 'Game Mode' page 
	 AND that the user has a CSV file with a list of word pairs on his/her device (assigned by the teacher)
	 AND that the user has selected the "GENERATE YOUR OWN PUZZLE" option
* When: the user uploads a CSV file in the format specified on the screen
* Then: the user is directed to the "Select Language Mode" page and can proceed to select a language mode, enable or disable Listening Comprehension Mode and start a randomly generated puzzle created from the set of words provided/loaded 

--------------------------------------------------------------------------------

**User Story 10:** 
As a student working with a textbook, I want to load pairs of words to practice from each chapter of the book.

*Scenario 1*
* Given: that the user selects the "PLAY" option from the launch page
         AND the user is redirected to the 'Game Mode' page 
	 AND the user has a CSV file with a list of word pairs on his/her device  
* When: the user selects the "GENERATE YOUR OWN PUZZLE" option 
* Then: the user can upload a CSV file containing pairs of words in the format specified on the screen

*Scenario 2*
* Given: that the user selects the "PLAY" option from the launch page
         AND the user is redirected to the 'Game Mode' page 
	 AND the user has a CSV file with a list of word pairs on his/her device 
	 AND the user has selected the "GENERATE YOUR OWN PUZZLE" option
* When: the user uploads a CSV file (containing pairs of words) in the format specified on the screen
* Then: the user is directed to the "Select Language Mode" page and can proceed to select a language mode, enable or disable Listening Comprehension Mode and start a randomly generated puzzle created from the set of words provided/loaded 

--------------------------------------------------------------------------------

**User Story 11:** 
As a student who wants to practice my understanding of spoken words in the language that I am learning, I want a listening comprehension mode. In this mode, numbers will appear in the pre-filled cells and the corresponding word in the language that I am learning will be read out to me when I press the number.

*Scenario 1*
* Given: that the user has enabled listening comprehension mode
* When: the user initiates a new puzzle
* Then: the user sees a standard Sudoku grid with some prefilled cells showing digits in the range 1..9 and all other cells empty

*Scenario 2*
* Given: that the user is filling in the grid in listening comprehension mode,
         AND that the grid includes a cell with the prefilled digit 4
	 AND that word pair 4 is (green, vert)
* When: the user presses the prefilled cell having the digit 4
* Then: the user hears the word "vert" read out and pronounced in French

*Scenario 3*
* Given: that the user is filling in the grid in listening comprehension mode,
	 AND that the grid includes a cell with the prefilled digit 4
	 AND that word pair 4 is (green, vert)
* When: the user selects a non-prefilled cell to enter the word "green"
* Then: the word "green" appears in the menu (list of words that may be selected), but not in the fourth position

*Scenario 4*
* Given: that the user is filling in the grid in listening comprehension mode
* When: the user presses a cell and hears the word "vert"
* Then: the user does not see the word "vert" anywhere on the game grid (unless the user is unlocking a hint by tapping the cell twice)

*Scenario 5*
* Given: that the user is filling in the grid in English -> French mode with listening comprehension enabled 
	 AND that the user presses a cell and hears the word "vert" 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user taps on the same cell again to unlock a hint
* Then: the word "vert" will be pronounced again and the word "vert" will appear on the screen briefly 

*Scenario 6*
* Given: that the user is filling in the grid in French -> English mode with listening comprehension enabled 
	 AND that the user presses a cell and hears the word "vert" 
	 AND that a message has appeared notifying the user to "Tap on the box again for a hint!"
* When: the user taps on the same cell again to unlock a hint
* Then: the word "vert" will be pronounced again and the word "green" will appear on the screen briefly

--------------------------------------------------------------------------------

**User Story 12:** 
As a language learner, I want to be able to enter words into the sudoku grid as I play and practice listening comprehension and recognition without having to worry too much about spelling mistakes and language keyboards. 

*Scenario 1*
* Given: that the user has initiated a puzzle 
	 AND that a menu with the list of words that can be entered into the grid is located on the screen
* When: the user clicks on a fillable cell (i.e. not a pre-filled cell) in the grid and subsequently clicks on a word from the menu
* Then: the specified word will appear in the specified cell in the sudoku grid (No typing of words is required for entry/placement of words into the grid by the user)
	AND the cell will be shaded green to distinguish cells with user input from cells that are part of the pre-filled configuration

--------------------------------------------------------------------------------

**User Story 13:** 
As a language learner, I want to be able to identify which cells I have recently manipulated (by entering words) so far so I can visually track my recent progress and easily distinguish between words I have recently entered and words that are part of the pre-filled configuration. 

*Scenario 1*
* Given: that the user has initiated a puzzle  
* When: the user presses an empty cell of the grid to enter a word from the menu into the cell
* Then: the selected cell is shaded green and word that was selected from the word menu will be placed in the grid cell most recently tapped

--------------------------------------------------------------------------------

**User Story 14:**
As a language learner, I want the sudoku grid to have a random pre-filled configuration (i.e. be pre-filled with words or numbers at random locations) so that I can stay engaged every time I practice new words with a sudoku puzzle.

*Scenario 1*
* Given: that the user launches the app and selects "PLAY"
* When: the user initiates a puzzle
* Then: the application's random sudoku generator generates a different/random sudoku puzzle (i.e. configuration of pre-filled cells in puzzle is randomized) 

--------------------------------------------------------------------------------

**User Story 15:**
As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3).


*Scenario 1*
* Given: that the user has selected "PLAY" 
	 AND the user is directed to the "Game Mode" page of the application 
* When: the user selects the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
* Then: the user is directed to the "Select Language Mode" of the application and sees that there is a drop-down menu which contains the following options ("DEFAULT: 9x9 grid", "4x4 grid", "6x6 grid", "12x12 grid")


*Scenario 2*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" page of the application
	 AND that the user has pressed on the drop-down menu on the "Select Language Mode" page
* When: the user selects the "4x4 grid" option from the drop-down menu and initiates the puzzle by selecting a mode (i.e. English -> French)
* Then: the user sees a sudoku grid of size 4x4 (partially pre-filled with English words) and 4 words in the menu (in French)

*Scenario 3*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" page of the application
	 AND that the user has pressed on the drop-down menu on the "Select Language Mode" page
* When: the user selects the "6x6 grid" option from the drop-down menu and initiates the puzzle by selecting a mode (i.e. English -> French)
* Then: the user sees a sudoku grid of size 6x6 (partially pre-filled with English words) with subgrids of size 2x3 and a menu consisting of 6 words (in French)


--------------------------------------------------------------------------------

**User Story 16:**
As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4).

*Scenario 1*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" of the application
* When: the user is setting up the language mode and enabling or disabling listening comprehension mode
* Then: the user sees that there is a drop-down menu which contains the following options ("DEFAULT: 9x9 grid", "4x4 grid", "6x6 grid", "12x12 grid")

*Scenario 2*
* Given: that the user has selected "PLAY"
	 AND that the user has selected the option "DEFAULT PUZZLE" on the "Game Mode" page of the application 
	 AND that the user is directed to the "Select Language Mode" of the application
	 AND that the user has pressed on the drop-down menu on the "Select Language Mode" page
* When: the user selects the "12x12 grid" option from the drop-down menu and initiates the puzzle by selecting a mode (i.e. English -> French)
* Then: the user sees a sudoku grid of size 12x12 (partially pre-filled with English words) with subgrids of size 3x4 and a menu consisting of 12 words (in French)

--------------------------------------------------------------------------------

**User Story 17:**
As a user, I want to be able to continue a puzzle where I left off- if I have not yet completed the puzzle. 

*Scenario 1*
* Given: that the user has selected to "PLAY" 
	 AND that the user has initiated a puzzle
* When: the user leaves the page
* Then: the current state of the game (including user filled grid cells) is automatically saved and can be accessed again by selecting "Continue" on the 'Game Mode' page of the application

*Scenario 2*
* Given: that the user has selected to "PLAY" 
	 AND is directed to the "Game Mode" page of the application
* When: the user selects "Continue"
* Then: the user can continue the most recent game that was saved 

--------------------------------------------------------------------------------
**User Story 18:**
As a user, I want the application's icon to be distinguishable from other applications on my device. As a potential user searching the app store, I would like to be able to get a sense of what the app is about based on the icon. 

*Scenario 1*
* Given: that the user has downloaded Wudoku 
* When: the user is looking for our application amongst all the other apps on their device
* Then: the user can recognize our application by its distinctive app icon 

*Scenario 2*
* Given: that the user has not yet downloaded Wudoku
* When: the user is looking for the Wudoku application in the app store
* Then: the user can look at the application icon to get a sense of what the Wudoku application is about

--------------------------------------------------------------------------------

**User Story 19:**
As a user, I want to have a weighted score to keep track of my progress on completed Wudoku puzzles

*Scenario 1*
* Given: that the user has initiated a 9x9 puzzle 
* When: the user completes a 9x9 puzzle
* Then: the score is increased by 9 points 

*Scenario 2*
* Given: that the user has initiated a 4x4 puzzle 
* When: the user completes a 4x4 puzzle
* Then: the score is increased by 4 points 

*Scenario 3*
* Given: that the user has initiated a 6x6 puzzle 
* When: the user completes a 6x6 puzzle
* Then: the score is increased by 6 points 

*Scenario 4*
* Given: that the user has initiated a 12x12 puzzle 
* When: the user completes a 12x12 puzzle
* Then: the score is increased by 12 points 

---------------------------------------------------------------------------------
**User Story 20:**
As a user, I want to be able to be customize the colour scheme of the application

*Scenario 1*
* Given: that the user has launched the application
* When: the user selects a colour scheme from a menu on the launch page
* Then: the application's colour scheme (background) will be changed to the selected colour scheme

*Scenario 2*
* Given: that the user has launched the application
* When: the user selects the light mode option for the colour scheme
* Then: the application's colour scheme (background) will be adapted for light mode

*Scenario 3*
* Given: that the user has launched the application
* When: the user selects the dark mode option for the colour scheme 
* Then: the application's colour scheme (background) will be adapted for dark mode

--------------------------------------------------------------------------------

**User Story 21:**
As a user, I want to be able to spread the word about this application (by sharing my current score) to my friends so they can also begin learning a new language in a fun way!

*Scenario 1*
* Given: that the user has downloaded Wudoku on their device 
* When:  the user has pressed the "Share" button on the front/launch page of the application
* Then: the user can share his/her score and spread the word to his/her friends about Wudoku

--------------------------------------------------------------------------------

**User Story 22:**
As a user, I want some more options for language modes. 

*Scenario 1*
* Given: that the user has selected "PLAY"
* When:  the user selects "DEFAULT PUZZLE" on the "Game Mode" page
* Then: the user is directed to the 'Select Language Mode' page and can see some more options for language modes (i.e. English -> Spanish and Spanish -> English)

--------------------------------------------------------------------------------
