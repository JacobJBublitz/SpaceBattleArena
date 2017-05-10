pipeline {
	agent any

	stages {
		stage('Build') {
			steps {
				sh './gradlew assemble'
				archiveArtifacts artifacts: 'build/distributions/*.zip'
			}
		}
	}
}
