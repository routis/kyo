package kyo.llm.thoughts

import kyo.llm._

case class Elaborate(
    `Analyze the thoughts so far to plan your reply`: true,
    `Review the key information provided by the user`: true,
    `Outline of the information to be generated in toolInput`: String,
    `Topics to cover`: List[String]
) extends Thought