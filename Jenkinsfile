pipeline {
	agent any

	stages {
		stage('Build') {
			steps {
				if (isUnix()) {
					sh './gradlew assemble'
				} else {
					bat 'gradlew.bat assemble'
				}
				archiveArtifacts artifacts: 'build/distributions/*.zip'
			}
		}
	}
}
