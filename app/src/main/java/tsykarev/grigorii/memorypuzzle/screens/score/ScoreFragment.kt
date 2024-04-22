package tsykarev.grigorii.memorypuzzle.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import tsykarev.grigorii.memorypuzzle.R
import tsykarev.grigorii.memorypuzzle.databinding.ScoreFragmentBinding

class ScoreFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.score_fragment, container, false)

        binding.MenuButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_scoreFragment_to_gameFragment)
        }

        return binding.root
    }
}