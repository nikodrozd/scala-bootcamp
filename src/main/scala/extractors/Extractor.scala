package extractors

object Extractor {

  val inUaStr: String = "in.ua"
  val gmailComStr: String = "gmail.com"

  def filterNotDomainsWithParticularHead(domains: List[String], head: String = inUaStr): List[String] = {
    domains.filterNot {
      case Domain(f, s, _*) if s"$s.$f" == head => true
      case _ => false
    }
  }

  def filterEmailsWithParticularDomain(emails: Array[String], inputDomainStr: String = gmailComStr): Array[Unit] = {
    for (email <- emails) yield {
      email match {
        case Email(_, domain) if domain == inputDomainStr => println(email)
        case _ =>
      }
    }
  }

}

object Email {

  def apply(localPart: String, domain: String) = s"$localPart@$domain"

  def unapply(email: String): Option[(String, String)] = {
    email.split("@") match {
      case Array(localPart, domain) => Some((localPart, domain))
      case _ => None
    }
  }

}

object Domain {

  def unapplySeq(domain: String): Option[Array[String]] = {
    Some(domain.split("\\.").reverse)
  }
}
