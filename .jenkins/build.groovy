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
   //stage('SonarQube analysis') {
   // withSonarQubeEnv('sonarqube72') {
   //   // requires SonarQube Scanner for Maven 3.2+
   //   sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
   // }
   //}
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
      docker.withRegistry('https://repository.tortuga.prv') {
      def customImageLast = docker.build("myproject:latest", "--no-cache .")
      customImageLast.push()
      def customImage = docker.build("myproject:${env.BUILD_ID}", "--no-cache .")
      customImage.push()
      }
   }
   stage('Deploy') {
      kubernetesDeploy(
      	kubeconfigId: 'admin-kube',
        configs: '.k8s/kubeconfig.yml',
        enableConfigSubstitution: true,
        secretNamespace: 'test',
        secretName: 'kubeadmin',
        serverUrl: 'https://repository.tortuga.prv'
		)
   }
}
