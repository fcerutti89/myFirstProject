node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/fcerutti89/myFirstProject'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // ** in the global configuration. 
      mvnHome = tool 'M3'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('Dockerbuild') {
      checkout scm
      docker.withRegistry('http://10.142.0.3') {
      def customImage = docker.build("myFirstProject:${env.BUILD_ID}", "./.docker/build")
      customImage.push()
      }
   }
//   stage('Upload dockerfile in repository') {
//      echo "upload"
//   }
}