
#How to update Node.JS which is used by npm
brew update
#If the first time installing node.js
brew install node 

#If needing to upgrade node.js
brew upgrade node


#Use this to install NPM.
npm install -g @angular/cli


#https://angular.io/cli
#New Angular project

ng new <project-name>


#Starting the project
ng serve


#from the browser go to localhost:4200


#installing bootstrap
npm install --save bootstrap@3


#Angular CLI
ng new <angular-application>

ng g c <component-name>  

#If you want to create a component with the testing spec file.
ng g c <component-name> --spec false 

#If you want to create a component with the testing spec file.
ng g c <parent-component>/<component-name> --spec false 
