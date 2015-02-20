# Import the corpus and functions used from nltk library
import os, sys, re
from nltk.corpus import brown
from nltk.corpus import cess_cat
from nltk.corpus import nps_chat
from nltk.probability import LidstoneProbDist
from nltk.model import NgramModel
from nltk.tokenize import word_tokenize, wordpunct_tokenize # Tokenizer
from nltk.tokenize import RegexpTokenizer

if __name__ == "__main__":
    urlRegex = '(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?'
    specRegex = "([#@]+[\w']+)"
    symbolsRegex = '[\^=<>.,!?:;\(\)_\"]+'
    simpleWordRegex = "[\w'-]+"

    tTwit = list(nps_chat.words())

    # estimator for smoothing the N-gram model
    estimator1 = lambda fdist, bins: LidstoneProbDist(fdist, 0.2)

    tokens1 = list(brown.words())

    # N-gram language model with 3-grams
    model = NgramModel(3, tokens1, estimator=estimator1)

    twitsFile = sys.argv[1]
    varsFile = sys.argv[2]
    outFile = sys.argv[3]
    outTwitFile = sys.argv[4]
    mode = sys.argv[5]

    # open files
    f = open(twitsFile)
    twits = f.readlines()
    f.close()
    f = open(varsFile)
    vars = f.readlines()
    f.close()
    f = open(outFile, 'w')
    ftwit = open(outTwitFile, 'w')

    for k in range(len(twits)):
        twit = twits[k]
        varLine = vars[k]

        posVars = list()
        posVars = varLine.split('~')
        del posVars[-1]
        posVars = filter(lambda a: a != '~', posVars)

        #        tTwit = word_tokenize(twit)
        tokenizer = RegexpTokenizer(urlRegex + '|' + symbolsRegex + '|' + specRegex + '|' + simpleWordRegex)
        tTwit = tokenizer.tokenize(twit)

        print '^^^^' + ' '.join(tTwit)

        tTwitOriginal = tTwit
        #        print 'posVars ' + ' '.join(posVars)

        #process # and @
        #if ('#' in tTwit or '@' )
        #for i in range(len(tTwit))

        types = list()
        j = 0
        for i in range(len(tTwit)):
            print '*****' + tTwit[i]
            if re.match(symbolsRegex, tTwit[i]) is None:
                if re.match(urlRegex, tTwit[i]) is None:
                    if re.match(specRegex, tTwit[i]) is None:
                        pVars = posVars[j]
                        print ' vars ' + pVars
                        print i
                        print j
                        tokensVars = pVars.split('*')
                        if tokensVars[-1] is not '*':
                            types.append(tokensVars[-1])
                        del tokensVars[-1]
                        tokensVars = filter(lambda a: a != '*', tokensVars)
                        perp = ""
                        res = ""
                        for element in tokensVars:
                            tTwit[i] = element
                            #                print 'iter ' + ' '.join(tTwit)
                            perplexity = model.perplexity(tTwit)
                            perplexity = 0
                            if perplexity < perp:
                                perp = perplexity
                                res = element
                        tTwit[i] = res
                    else:
                        types.append('NO')
                    j = j + 1
                else:
                    types.append('NO')
            else:
                types.append('NO')

        print '===='
        print twit
        s = ' '.join(tTwit)
        print s
        print tTwit
        if mode == 'simpleMode':
#            print s
            ftwit.write(s + '\n')
        if mode == 'testMode':
#            print tTwit
            f.write(tTwit.__str__() + '\n')
            f.write(types.__str__() + '\n')
            ftwit.write(s + '\n')
    f.close()
    ftwit.close()

