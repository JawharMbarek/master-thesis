package de.m0ep.uni.ma.rdf.vocabularies;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Fri Dec 28 17:12:35 CET 2012
 * input file: src/main/resources/ontologies/sioc_types.rdf
 * namespace: http://rdfs.org/sioc/types#
 */
public interface SIOCTypes {
	public static final URI NS_SIOCTypes = new URIImpl("http://rdfs.org/sioc/types#",false);

    /**
     * Label: Address Book@en 
     * Comment: Describes a collection of personal or organisational addresses.@en 
     */
    public static final URI AddressBook = new URIImpl("http://rdfs.org/sioc/types#AddressBook", false);

    /**
     * Label: Annotation Set@en 
     * Comment: Describes a set of annotations, for example, those created by a particular user or related to a particular topic.@en 
     */
    public static final URI AnnotationSet = new URIImpl("http://rdfs.org/sioc/types#AnnotationSet", false);

    /**
     * Label: Audio Channel@en 
     * Comment: Describes a channel for distributing audio or sound files, for example, a podcast.@en 
     */
    public static final URI AudioChannel = new URIImpl("http://rdfs.org/sioc/types#AudioChannel", false);

    /**
     * Label: Bookmark Folder@en 
     * Comment: Describes a shared collection of bookmarks.@en 
     */
    public static final URI BookmarkFolder = new URIImpl("http://rdfs.org/sioc/types#BookmarkFolder", false);

    /**
     * Label: Briefcase@en 
     * Comment: Describes a briefcase or file service.@en 
     */
    public static final URI Briefcase = new URIImpl("http://rdfs.org/sioc/types#Briefcase", false);

    /**
     * Label: Event Calendar@en 
     * Comment: Describes a calendar of events.@en 
     */
    public static final URI EventCalendar = new URIImpl("http://rdfs.org/sioc/types#EventCalendar", false);

    /**
     * Label: Image Gallery@en 
     * Comment: Describes an image gallery, for example, a photo album.@en 
     */
    public static final URI ImageGallery = new URIImpl("http://rdfs.org/sioc/types#ImageGallery", false);

    /**
     * Label: Project Directory@en 
     * Comment: Describes a project directory.@en 
     */
    public static final URI ProjectDirectory = new URIImpl("http://rdfs.org/sioc/types#ProjectDirectory", false);

    /**
     * Label: Resume Bank@en 
     * Comment: Describes a collection of resumes.@en 
     */
    public static final URI ResumeBank = new URIImpl("http://rdfs.org/sioc/types#ResumeBank", false);

    /**
     * Label: Review Area@en 
     * Comment: Describes an area where reviews are posted.@en 
     */
    public static final URI ReviewArea = new URIImpl("http://rdfs.org/sioc/types#ReviewArea", false);

    /**
     * Label: Subscription List@en 
     * Comment: Describes a shared set of feed subscriptions.@en 
     */
    public static final URI SubscriptionList = new URIImpl("http://rdfs.org/sioc/types#SubscriptionList", false);

    /**
     * Label: Survey Collection@en 
     * Comment: Describes an area where survey data can be collected, e.g. from polls.@en 
     */
    public static final URI SurveyCollection = new URIImpl("http://rdfs.org/sioc/types#SurveyCollection", false);

    /**
     * Label: Video Channel@en 
     * Comment: Describes a channel for distributing videos (moving image) files, for example, a video podcast.@en 
     */
    public static final URI VideoChannel = new URIImpl("http://rdfs.org/sioc/types#VideoChannel", false);

    /**
     * Label: Wiki@en 
     * Comment: Describes a wiki space.@en 
     */
    public static final URI Wiki = new URIImpl("http://rdfs.org/sioc/types#Wiki", false);

    /**
     * Label: Favourite Things@en 
     * Comment: Describes a list or a collection of one's favourite things.@en 
     */
    public static final URI FavouriteThings = new URIImpl("http://rdfs.org/sioc/types#FavouriteThings", false);

    /**
     * Label: Offer List@en 
     * Comment: Describes a list of the items someone has available to offer.@en 
     */
    public static final URI OfferList = new URIImpl("http://rdfs.org/sioc/types#OfferList", false);

    /**
     * Label: Playlist@en 
     * Comment: Describes a list of media items that have been played or can be played.@en 
     */
    public static final URI Playlist = new URIImpl("http://rdfs.org/sioc/types#Playlist", false);

    /**
     * Label: Reading List@en 
     * Comment: Describes a list of books or other materials that have been read or are suggested for reading.@en 
     */
    public static final URI ReadingList = new URIImpl("http://rdfs.org/sioc/types#ReadingList", false);

    /**
     * Label: Wish List@en 
     * Comment: Describes a list of the items someone wishes to get.@en 
     */
    public static final URI WishList = new URIImpl("http://rdfs.org/sioc/types#WishList", false);

    /**
     * Label: Argumentative Discussion@en 
     * Comment: Describes a discussion area where logical arguments can take place.@en 
     */
    public static final URI ArgumentativeDiscussion = new URIImpl("http://rdfs.org/sioc/types#ArgumentativeDiscussion", false);

    /**
     * Label: Chat Channel@en 
     * Comment: Describes a channel for chat or instant messages, for example, via IRC or IM.@en 
     */
    public static final URI ChatChannel = new URIImpl("http://rdfs.org/sioc/types#ChatChannel", false);

    /**
     * Label: Mailing List@en 
     * Comment: Describes an electronic mailing list.@en 
     */
    public static final URI MailingList = new URIImpl("http://rdfs.org/sioc/types#MailingList", false);

    /**
     * Label: Message Board@en 
     * Comment: Describes a message board, also known as an online bulletin board or discussion forum.@en 
     */
    public static final URI MessageBoard = new URIImpl("http://rdfs.org/sioc/types#MessageBoard", false);

    /**
     * Label: Microblog@en 
     * Comment: Describes a microblog, i.e. a blog consisting of short text messages.@en 
     */
    public static final URI Microblog = new URIImpl("http://rdfs.org/sioc/types#Microblog", false);

    /**
     * Label: Weblog@en 
     * Comment: Describes a weblog (blog), i.e. an online journal.@en 
     */
    public static final URI Weblog = new URIImpl("http://rdfs.org/sioc/types#Weblog", false);

    /**
     * Label: Poll@en 
     * Comment: Describes a posted item that contains a poll or survey content.@en 
     */
    public static final URI Poll = new URIImpl("http://rdfs.org/sioc/types#Poll", false);

    /**
     * Label: Blog Post@en 
     * Comment: Describes a post that is specifically made on a weblog.@en 
     */
    public static final URI BlogPost = new URIImpl("http://rdfs.org/sioc/types#BlogPost", false);

    /**
     * Label: Board Post@en 
     * Comment: Describes a post that is specifically made on a message board.@en 
     */
    public static final URI BoardPost = new URIImpl("http://rdfs.org/sioc/types#BoardPost", false);

    /**
     * Label: Comment@en 
     * Comment: Comment is a subtype of sioc:Post and allows one to explicitly indicate that this SIOC post is a comment.  Note that comments have a narrower scope than sioc:Post and may not apply to all types of community site.@en 
     */
    public static final URI Comment = new URIImpl("http://rdfs.org/sioc/types#Comment", false);

    /**
     * Label: Instant Message@en 
     * Comment: Describes an instant message, e.g. sent via Jabber.@en 
     */
    public static final URI InstantMessage = new URIImpl("http://rdfs.org/sioc/types#InstantMessage", false);

    /**
     * Label: Mail Message@en 
     * Comment: Describes an electronic mail message, e.g. a post sent to a mailing list.@en 
     */
    public static final URI MailMessage = new URIImpl("http://rdfs.org/sioc/types#MailMessage", false);

    /**
     * Label: Microblog Post@en 
     * Comment: Describes a post that is specifically made on a microblog.@en 
     */
    public static final URI MicroblogPost = new URIImpl("http://rdfs.org/sioc/types#MicroblogPost", false);

    /**
     * Label: Wiki Article@en 
     * Comment: Describes a wiki article.@en 
     */
    public static final URI WikiArticle = new URIImpl("http://rdfs.org/sioc/types#WikiArticle", false);

    /**
     * Label: Answer@en 
     * Comment: A Post that provides an answer in reply to a Question.@en 
     */
    public static final URI Answer = new URIImpl("http://rdfs.org/sioc/types#Answer", false);

    /**
     * Label: Best Answer@en 
     * Comment: A Post that is the best answer to a Question, as chosen by the UserAccount who asked the Question or as voted by a Community of UserAccounts.@en 
     */
    public static final URI BestAnswer = new URIImpl("http://rdfs.org/sioc/types#BestAnswer", false);

    /**
     * Label: Question@en 
     * Comment: A Post that asks a Question.@en 
     */
    public static final URI Question = new URIImpl("http://rdfs.org/sioc/types#Question", false);

    /**
     * Label: Category@en 
     * Comment: Category is used on the object of sioc:topic to indicate that this resource is a category on a site.@en 
     */
    public static final URI Category = new URIImpl("http://rdfs.org/sioc/types#Category", false);

    /**
     * Label: Tag@en 
     * Comment: Tag is used on the object of sioc:topic to indicate that this resource is a tag on a site.@en 
     */
    public static final URI Tag = new URIImpl("http://rdfs.org/sioc/types#Tag", false);

}
