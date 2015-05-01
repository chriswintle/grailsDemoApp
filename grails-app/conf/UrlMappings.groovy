class UrlMappings {

	static mappings = {

        "/"	{
			controller	= 'project'
			action		= { 'listProjects' }

        }
		"/$controller/$action?/$id?"{
			constraints {
				controller(matches:/^((?!(api|mobile|web)).*)$/)
		  	}
		}

	}
}
