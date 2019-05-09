import com.mantono.ask.ask
import java.net.URI

fun main(args: Array<String>) {
	val defaultGithubOrg: String? = if(args.isEmpty()) null else args[0]

	val projectName: String = ask("Project name")
	val projectDescription = ask("Project description")
	val githubOrg: String = ask("Github user/organization", defaultGithubOrg)
	val groupName: URI = ask("Group name", URI("com.mantono"))
	/**
	 * Add git lib?
	 * 1. Project name
	 * 2. Project description
	 * 3. Github user
	 * 4. Group name
	 */
}