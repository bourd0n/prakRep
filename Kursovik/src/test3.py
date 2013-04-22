# Import the corpus and functions used from nltk library
import os, sys
from nltk.corpus import brown
from nltk.corpus import cess_cat
from nltk.probability import LidstoneProbDist
from nltk.model import NgramModel
from nltk.tokenize import word_tokenize, wordpunct_tokenize # Tokenizer
if __name__ == "__main__":

#add language
    tTwit = list(brown.words())
#    tTwit.extend(list(cess_cat.words()))

    # estimator for smoothing the N-gram model
    estimator1 = lambda fdist, bins: LidstoneProbDist(fdist, 0.2)

    tokens1 = list(brown.words())
    # N-gram language model with 3-grams
    model = NgramModel(3, tokens1, estimator=estimator1)


    twit = sys.argv[1]
    #posVars = sys.argv[2]
    #pos = sys.argv[3]

    posVars = list()
    tmpVars = list()
    for i in range(2, len(sys.argv)):
        posVars.append(sys.argv[i])
#        print sys.argv[i]

    tTwit = word_tokenize(twit)
    #tokens2 = word_tokenize(posVars)
#    print 'twit ' + ' '.join(tTwit)
    print 'posVars ' + ' '.join(posVars)
    #posI = int(pos)
    #perp = ""
    #res = ""

    for i in range(len(tTwit)):
#        print '------'
        pVars = posVars[i]
#        tokensVars = word_tokenize(pVars)
        print ' vars ' + pVars
#        tokensVars = wordpunct_tokenize(pVars)
        tokensVars = pVars.split(',')
        del tokensVars[-1]
#        print '.'.join(tokensVars)
        tokensVars = filter (lambda a: a != ',', tokensVars)
#        print ';'.join(tokensVars)
#        print ' '.join(tokensVars)
        perp = ""
        res = ""
        for element in tokensVars:
            tTwit[i] = element
            print 'iter ' + ' '.join(tTwit)
            perplexity = model.perplexity(tTwit)
            if perplexity < perp :
                perp = perplexity
                res = element
#        print res
        tTwit[i] = res

#    for element in tTwit:
#        tTwit[posI] = element
#        str = ' '.join(tTwit)
#        perplexity = model.perplexity(tTwit)
#        print str + " " + "%f" % float(perplexity)
#        if perplexity < perp :
#            perp = perplexity
#            res = element

        # print the text
    print '===='
    print twit
    #print "perp: " + "%f" % float(perp)
    #print " result: " + res
    #print res
    print ' '.join(tTwit)

