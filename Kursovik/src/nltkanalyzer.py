# Import the corpus and functions used from nltk library
import os, sys
from nltk.corpus import brown
from nltk.corpus import cess_cat
from nltk.corpus import nps_chat
from nltk.probability import LidstoneProbDist
from nltk.model import NgramModel
from nltk.tokenize import word_tokenize, wordpunct_tokenize # Tokenizer

if __name__ == "__main__":
    tTwit = list(nps_chat.words())

    # estimator for smoothing the N-gram model
    estimator1 = lambda fdist, bins: LidstoneProbDist(fdist, 0.2)

    tokens1 = list(brown.words())

    # N-gram language model with 3-grams
    model = NgramModel(3, tokens1, estimator=estimator1)

    twitsFile = sys.argv[1]
    varsFile = sys.argv[2]
    outFile = sys.argv[3]

    # open files
    f = open(twitsFile)
    twits = f.readlines()
    f.close()
    f = open(varsFile)
    vars = f.readlines()
    f.close()
    f = open(outFile, 'w')

    for k in range(len(twits)):
        twit = twits[k]
        varLine = vars[k]

        posVars = list()
        posVars = varLine.split(';')
        del posVars[-1]
        posVars = filter(lambda a: a != ';', posVars)

        tTwit = word_tokenize(twit)
#        print 'posVars ' + ' '.join(posVars)

        for i in range(len(tTwit)):
            pVars = posVars[i]
#            print ' vars ' + pVars
            tokensVars = pVars.split(',')
            del tokensVars[-1]
            tokensVars = filter(lambda a: a != ',', tokensVars)
            perp = ""
            res = ""
            for element in tokensVars:
                tTwit[i] = element
#                print 'iter ' + ' '.join(tTwit)
                perplexity = model.perplexity(tTwit)
                if perplexity < perp:
                    perp = perplexity
                    res = element
            tTwit[i] = res

        print '===='
        print twit
        s = ' '.join(tTwit)
        print s
        f.write(s + '\n')

    f.close()

