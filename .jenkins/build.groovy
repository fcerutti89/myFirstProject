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
      docker.withRegistry('https://repo.tortuga.prv') {
      def customImage = docker.build("myfirstproject:${env.BUILD_ID}", ".")
      customImage.push()
      def customImageL = docker.build("myfirstproject:lastest", ".")
      customImageL.push()
      }
   }
   stage('Deploy') {
      kubernetesDeploy(
      	kubeconfigId: 'admin-kube',
        configs: '.k8s/kubeconfig.yml'
		)
   }
}